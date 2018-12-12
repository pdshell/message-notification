package com.mwt.wallet.message.notification.service;

import com.mwt.wallet.message.notification.Constant.ETHConstant;
import com.mwt.wallet.message.notification.client.CoinidClient;
import com.mwt.wallet.message.notification.domain.MessageNotificationRecord;
import com.mwt.wallet.message.notification.repository.MessageNotificationRecordRepository;
import com.mwt.wallet.message.notification.web.pojo.eth.*;
import com.mwt.wallet.message.notification.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessagesService {

    @Autowired
    private CoinidClient coinidClient;

    @Autowired
    private MessageNotificationRecordRepository messageNotificationRecordRepository;


    public TransactionReceipt getTransactionReceipt(String hash) {
        CoinIdVM coinId = new CoinIdVM();
        List<String> param = new ArrayList<>();
        param.add(hash);
        coinId.setJsonrpc(ETHConstant.JSON_RPC.getName());
        coinId.setMethod(ETHConstant.METHOD_TRANSACTIONRECEIPT.getName());
        coinId.setId(Integer.parseInt(ETHConstant.ID.getName()));
        coinId.setParams(param);
        return coinidClient.coinIdTransactionReceipt(coinId).getResult();
    }

    public TransactionInfo getTransactionByHash(String hash) {
        CoinIdVM coinId = new CoinIdVM();
        List<String> param = new ArrayList<>();
        param.add(hash);
        coinId.setJsonrpc(ETHConstant.JSON_RPC.getName());
        coinId.setMethod(ETHConstant.METHOD_TRANSACTIONBYHASH.getName());
        coinId.setId(Integer.parseInt(ETHConstant.ID.getName()));
        coinId.setParams(param);
        return coinidClient.coinIdTransactionByHash(coinId).getResult();
    }

    public String ethMessageNotification(AddressHash addressHash) {
        long start = System.currentTimeMillis();
        while (true) {
            TransactionReceipt.ResultBean resultBean = getTransactionReceipt(addressHash.getHash()).getResult();
            if (Optional.ofNullable(resultBean).isPresent() && !Optional.ofNullable(resultBean.getRoot()).isPresent()) {
                String status = Integer.parseInt(resultBean.getStatus().substring(2), 16) == 1 ? "success" : "failure";
                TransactionInfo.ResultBean transactionInfo = getTransactionByHash(addressHash.getHash()).getResult();
                String value = StringUtils.ethBalanceConvert(Long.parseLong(transactionInfo.getValue().substring(2), 16) + "");
                String message ="从" + resultBean.getFrom() + "账户转账到" + resultBean.getTo() + "账户 " + value + "转账" + status;
                MessageNotificationRecord messageNotification= new MessageNotificationRecord();
                messageNotification.setHash(addressHash.getHash());
                messageNotification.setMessage(message);
                messageNotification.setCreateTime(System.currentTimeMillis());
                messageNotificationRecordRepository.save(messageNotification);
                return message;
            } else if (start - System.currentTimeMillis() == 24 * 60 * 60 * 1000) {
                return "Transaction failure";
            }
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
