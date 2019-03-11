package com.mwt.wallet.message.notification.job;

import com.mwt.wallet.message.notification.Constant.BlockChain;
import com.mwt.wallet.message.notification.Constant.TransactionStateConstant;
import com.mwt.wallet.message.notification.repository.redis.TransactionStorageRepository;
import com.mwt.wallet.message.notification.service.BtcMessagesService;
import com.mwt.wallet.message.notification.service.EthMessagesService;
import com.mwt.wallet.message.notification.service.VNSMessagesService;
import com.mwt.wallet.message.notification.web.pojo.TransactionStorageRQ;
import com.mwt.wallet.message.notification.web.pojo.btc.TransactionRQ;
import com.mwt.wallet.message.notification.web.pojo.eth.TransactionInfo;
import com.mwt.wallet.message.notification.web.pojo.eth.TransactionReceipt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class MessageScheduler {

    @Autowired
    private TransactionStorageRepository transactionStorageRepository;

    @Autowired
    private BtcMessagesService btcMessagesService;

    @Autowired
    private EthMessagesService ethMessagesService;

    @Autowired
    private VNSMessagesService vnsMessagesService;

    @Scheduled(cron = "0/3 * * * * ? ")
    public void updateMessages() {
        transactionStorageRepository.findAllByStatus(0).forEach(transactionStorageRQ -> {
            if (transactionStorageRQ.getType().contains("|"))
                transactionStorageRQ.setType(transactionStorageRQ.getType().substring(0, transactionStorageRQ.getType().indexOf("|")));
            switch (transactionStorageRQ.getType()) {
                case "BTC":
                    btcMessage(transactionStorageRQ);
                    break;
                case "VNS":
                    ethAndVnsMessage(transactionStorageRQ);
                    break;
                case "ETH":
                    ethAndVnsMessage(transactionStorageRQ);
                    break;
            }
        });
    }

    private void btcMessage(TransactionStorageRQ transactionStorageRQ) {
        TransactionRQ transactionRQ = btcMessagesService.getTransactionByTxHash(transactionStorageRQ.getTrxId(), true);
        if (Optional.ofNullable(transactionRQ.getResult()).isPresent() && transactionRQ.getResult().getConfirmations() > 0) {
            transactionStorageRQ.setStatus(1);
            transactionStorageRepository.save(transactionStorageRQ);
            JiguangPush.jiguangPush(transactionStorageRQ.getFrom(), transactionStorageRQ.getTo(), transactionStorageRQ.getValue() + " " + transactionStorageRQ.getType(), "SUCCESS");
        }
    }

    private void ethAndVnsMessage(TransactionStorageRQ transactionStorageRQ) {
        TransactionInfo transactionInfo = transactionStorageRQ.getType().equals(BlockChain.ETH.getName().toUpperCase())
                ? ethMessagesService.getTransactionByHash(transactionStorageRQ.getTrxId())
                : vnsMessagesService.getTransactionByHash(transactionStorageRQ.getTrxId());
        if (Optional.ofNullable(transactionInfo.getResult()).isPresent()
                && Optional.ofNullable(transactionInfo.getResult().getBlockNumber()).isPresent()) {
            transactionStorageRQ.setStatus(1);
            transactionStorageRepository.save(transactionStorageRQ);
            TransactionReceipt.ResultBean result = transactionStorageRQ.getType().equals(BlockChain.ETH.getName().toUpperCase()) ?
                    ethMessagesService.getTransactionReceipt(transactionStorageRQ.getTrxId()).getResult()
                    : vnsMessagesService.getTransactionReceipt(transactionStorageRQ.getTrxId()).getResult();
            JiguangPush.jiguangPush(transactionStorageRQ.getFrom(), transactionStorageRQ.getTo(), transactionStorageRQ.getValue() + " " + transactionStorageRQ.getType(), Integer.parseInt(result.getStatus().substring(2), 16) == 1
                    ? TransactionStateConstant.SUCCESS + ""
                    : TransactionStateConstant.FAILURE + "");
        }
    }

}
