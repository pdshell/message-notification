package com.mwt.wallet.message.notification.service;

import com.mwt.wallet.message.notification.Constant.BlockChain;
import com.mwt.wallet.message.notification.Constant.VNSConstant;
import com.mwt.wallet.message.notification.client.VNSCoinidClient;
import com.mwt.wallet.message.notification.util.StringUtils;
import com.mwt.wallet.message.notification.web.pojo.CoinIdVM;
import com.mwt.wallet.message.notification.web.pojo.eth.BlockNumber;
import com.mwt.wallet.message.notification.web.pojo.eth.NotificationRQ;
import com.mwt.wallet.message.notification.web.pojo.eth.TransactionInfo;
import com.mwt.wallet.message.notification.web.pojo.eth.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VNSMessagesService {

    @Autowired
    private VNSCoinidClient vnsCoinidClient;

    @Autowired
    private EthMessagesService ethMessagesService;

    public TransactionReceipt getTransactionReceipt(String hash) {
        List<Object> param = new ArrayList<>();
        param.add(hash);
        CoinIdVM coinId = StringUtils.getParameter(param, VNSConstant.METHOD_TRANSACTIONRECEIPT.getName());
        return vnsCoinidClient.coinIdTransactionReceipt(coinId).getResult();
    }

    public TransactionInfo getTransactionByHash(String hash) {
        List<Object> param = new ArrayList<>();
        param.add(hash);
        CoinIdVM coinId = StringUtils.getParameter(param, VNSConstant.METHOD_TRANSACTIONBYHASH.getName());
        return vnsCoinidClient.coinIdTransactionByHash(coinId).getResult();
    }

    public TransactionInfo getTransactionByBlockNumberAndIndex(String blockNumber, String index) {
        List<Object> param = new ArrayList<>();
        param.add(blockNumber);
        param.add(index);
        CoinIdVM coinId = StringUtils.getParameter(param, VNSConstant.METHOD_TRANSACTIONBYBLOCKNUMBERANDINDEX.getName());
        return vnsCoinidClient.coinIdTransactionByHash(coinId).getResult();
    }


    public BlockNumber blockNumber() {
        CoinIdVM coinId = StringUtils.getParameter(null, VNSConstant.METHOD_BLOCKNUMBER.getName());
        return vnsCoinidClient.coinIdBlockNumber(coinId).getResult();
    }

    public Object getBalance(String account, String blockStatus) {
        List<Object> param = new ArrayList<>();
        param.add(account);
        param.add(blockStatus);
        CoinIdVM coinId = StringUtils.getParameter(param, VNSConstant.METHOD_BALANCE.getName());
        return vnsCoinidClient.ethChainInfo(coinId).getResult();
    }


    public List<NotificationRQ> vnsMessageNotification(String addr) {
        return ethMessagesService.messageNotification(BlockChain.VNS.getName().toUpperCase(), addr);
    }

    public List<NotificationRQ> vnsMessageNotificationList(String addr, Integer start, Integer limit) {
        return ethMessagesService.messageNotificationList(BlockChain.VNS.getName().toUpperCase(), addr, start, limit);
    }

}
