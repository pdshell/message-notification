package com.mwt.wallet.message.notification.service;

import com.mwt.wallet.message.notification.Constant.ETHConstant;
import com.mwt.wallet.message.notification.Constant.TransactionStateConstant;
import com.mwt.wallet.message.notification.Constant.VNSConstant;
import com.mwt.wallet.message.notification.client.EthCoinidClient;
import com.mwt.wallet.message.notification.client.VNSCoinidClient;
import com.mwt.wallet.message.notification.util.StringUtils;
import com.mwt.wallet.message.notification.web.pojo.CoinIdVM;
import com.mwt.wallet.message.notification.web.pojo.eth.BlockNumber;
import com.mwt.wallet.message.notification.web.pojo.eth.NotificationRQ;
import com.mwt.wallet.message.notification.web.pojo.eth.TransactionInfo;
import com.mwt.wallet.message.notification.web.pojo.eth.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VNSMessagesService {

    @Autowired
    private VNSCoinidClient vnsCoinidClient;

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


    public NotificationRQ vnsMessageNotification(String trxId) {
        NotificationRQ notification = new NotificationRQ();
        BlockNumber blockNumber = blockNumber();
        TransactionInfo transactionInfo = getTransactionByHash(trxId);
        if (Optional.ofNullable(transactionInfo.getResult().getBlockNumber()).isPresent()
                && new Long(blockNumber.getResult()) - new Long(transactionInfo.getResult().getBlockNumber()) > 6) {
            String value = transactionInfo.getResult().getValue().equals("0x0") ? "0 VNS"
                    : StringUtils.ethBalanceConvert(new BigInteger(transactionInfo.getResult().getValue().substring(2), 16) + "");
            notification.setValue(value);
            notification.setFrom(transactionInfo.getResult().getFrom());
            notification.setTo(transactionInfo.getResult().getTo());
            TransactionReceipt.ResultBean result = getTransactionReceipt(trxId).getResult();
            notification.setState(Integer.parseInt(result.getStatus().substring(2), 16) == 1
                    ? TransactionStateConstant.SUCCESS
                    : TransactionStateConstant.FAILURE);
        }
        return notification;
    }

}
