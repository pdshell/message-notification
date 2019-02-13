package com.mwt.wallet.message.notification.service;

import com.mwt.wallet.message.notification.Constant.BlockChain;
import com.mwt.wallet.message.notification.Constant.ETHConstant;
import com.mwt.wallet.message.notification.Constant.TransactionStateConstant;
import com.mwt.wallet.message.notification.client.EthCoinidClient;
import com.mwt.wallet.message.notification.repository.redis.TransactionStorageRepository;
import com.mwt.wallet.message.notification.util.StringUtils;
import com.mwt.wallet.message.notification.web.pojo.CoinIdVM;
import com.mwt.wallet.message.notification.web.pojo.TransactionStorageRQ;
import com.mwt.wallet.message.notification.web.pojo.eth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EthMessagesService {

    @Autowired
    private EthCoinidClient ethCoinidClient;

    @Autowired
    private TransactionStorageRepository transactionStorageRepository;

    public TransactionReceipt getTransactionReceipt(String hash) {
        List<Object> param = new ArrayList<>();
        param.add(hash);
        CoinIdVM coinId = StringUtils.getParameter(param, ETHConstant.METHOD_TRANSACTIONRECEIPT.getName());
        return ethCoinidClient.coinIdTransactionReceipt(coinId).getResult();
    }

    public TransactionInfo getTransactionByHash(String hash) {
        List<Object> param = new ArrayList<>();
        param.add(hash);
        CoinIdVM coinId = StringUtils.getParameter(param, ETHConstant.METHOD_TRANSACTIONBYHASH.getName());
        return ethCoinidClient.coinIdTransactionByHash(coinId).getResult();
    }

    public TransactionInfo getTransactionByBlockNumberAndIndex(String blockNumber, String index) {
        List<Object> param = new ArrayList<>();
        param.add(blockNumber);
        param.add(index);
        CoinIdVM coinId = StringUtils.getParameter(param, ETHConstant.METHOD_TRANSACTIONBYBLOCKNUMBERANDINDEX.getName());
        return ethCoinidClient.coinIdTransactionByHash(coinId).getResult();
    }


    public BlockNumber blockNumber() {
        CoinIdVM coinId = StringUtils.getParameter(null, ETHConstant.METHOD_BLOCKNUMBER.getName());
        return ethCoinidClient.coinIdBlockNumber(coinId).getResult();
    }

    public Object getBalance(String account, String blockStatus) {
        List<Object> param = new ArrayList<>();
        param.add(account);
        param.add(blockStatus);
        CoinIdVM coinId = StringUtils.getParameter(param, ETHConstant.METHOD_BALANCE.getName());
        return ethCoinidClient.ethChainInfo(coinId).getResult();
    }


    public List<NotificationRQ> ethMessageNotification(String addr) {
        return messageNotification(BlockChain.ETH, addr);
    }

    List<NotificationRQ> messageNotification(BlockChain blockChain, String addr) {
        Pageable pageable = PageRequest.of(1, 10, new Sort(Sort.Direction.DESC, "createTime"));
        Page<TransactionStorageRQ> transactionStorageRQPage = transactionStorageRepository.findByTypeAndFromOrTo(blockChain, addr, addr, pageable);
        List<NotificationRQ> notificationRQS = new ArrayList<>();
        transactionStorageRQPage.forEach(transactionStorageRQ -> {
//            BlockNumber blockNumber = blockNumber();
            TransactionInfo transactionInfo = getTransactionByHash(transactionStorageRQ.getTrxId());
            if (Optional.ofNullable(transactionInfo.getResult().getBlockNumber()).isPresent()) {
                String value = transactionInfo.getResult().getValue().equals("0x0") ? "0 " + blockChain.getName().toUpperCase()
                        : StringUtils.ethBalanceConvert(new BigInteger(transactionInfo.getResult().getValue().substring(2), 16) + "");
                NotificationRQ notification = new NotificationRQ();
                notification.setValue(value);
                notification.setTrxId(transactionStorageRQ.getTrxId());
                notification.setBlHeight(transactionInfo.getResult().getBlockNumber());
                notification.setGasPrice(transactionInfo.getResult().getGasPrice());
                notification.setGasLimit(transactionInfo.getResult().getGas());
                notification.setFrom(transactionInfo.getResult().getFrom());
                notification.setTo(transactionInfo.getResult().getTo());
                TransactionReceipt.ResultBean result = getTransactionReceipt(transactionStorageRQ.getTrxId()).getResult();
                notification.setTrxState(Integer.parseInt(result.getStatus().substring(2), 16) == 1
                        ? TransactionStateConstant.SUCCESS
                        : TransactionStateConstant.FAILURE);
                notification.setCreateTime(transactionStorageRQ.getCreateTime());
                notificationRQS.add(notification);
            }
        });
        return notificationRQS;
    }

    public NotificationRQ ethNotifyState(String hash) {
        TransactionInfo.ResultBean result = getTransactionByHash(hash).getResult();
        if (Optional.ofNullable(result).isPresent()) {
            NotificationRQ notificationRQ = new NotificationRQ();
            if (!Optional.ofNullable(result.getBlockNumber()).isPresent()) {
                notificationRQ.setFrom(result.getFrom());
                notificationRQ.setValue(result.getValue());
                notificationRQ.setTo(result.getTo());
                notificationRQ.setTrxState(TransactionStateConstant.PENDING);
                return notificationRQ;
            }
            TransactionReceipt.ResultBean receipt = getTransactionReceipt(result.getHash()).getResult();
            if (Optional.ofNullable(receipt).isPresent()) {
                notificationRQ.setFrom(result.getFrom());
                notificationRQ.setValue(result.getValue());
                notificationRQ.setTo(result.getTo());
                notificationRQ.setTrxState(Integer.parseInt(receipt.getStatus().substring(2), 16) == 1
                        ? TransactionStateConstant.SUCCESS
                        : TransactionStateConstant.FAILURE);
                return notificationRQ;
            }
        }
        return null;
    }

    public TransactionStateConstant getOrderState(String hash) {
        NotificationRQ notificationRQ = ethNotifyState(hash);
        if (Optional.ofNullable(notificationRQ).isPresent()) {
            return notificationRQ.getTrxState();
        }
        return TransactionStateConstant.PENDING;
    }
}
