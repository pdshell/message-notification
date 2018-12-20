package com.mwt.wallet.message.notification.service;

import com.mwt.wallet.message.notification.Constant.ETHConstant;
import com.mwt.wallet.message.notification.client.EthCoinidClient;
import com.mwt.wallet.message.notification.util.StringUtils;
import com.mwt.wallet.message.notification.web.pojo.CoinIdVM;
import com.mwt.wallet.message.notification.web.pojo.eth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EthMessagesService {

    @Autowired
    private EthCoinidClient ethCoinidClient;

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


    public List<String> ethMessageNotification() {
        BlockNumber blockNumber = blockNumber();
        long index = 0L;
        List<String> messages = new ArrayList<>();
        while (true) {
            TransactionInfo.ResultBean resultBean = getTransactionByBlockNumberAndIndex(blockNumber.getResult(), "0x" + Long.toHexString(index)).getResult();
//            String value = StringUtils.ethBalanceConvert(Long.parseLong(resultBean.getValue().substring(2), 16) + "");
            if (!Optional.ofNullable(resultBean).isPresent() && !messages.equals("[]")) {
                return messages;
            }
            TransactionReceipt.ResultBean result = getTransactionReceipt(resultBean.getHash()).getResult();
            String status = Integer.parseInt(result.getStatus().substring(2), 16) == 1 ? "success" : "failure";
            String message = index + ". 从" + resultBean.getFrom() + "账户转账到" + resultBean.getTo() + "账户 " + resultBean.getValue() + " 转账" + status;
            messages.add(message);
            index++;
        }
    }

    public String ethNotifyState(String hash) {
        TransactionInfo.ResultBean result = getTransactionByHash(hash).getResult();
        if (!Optional.ofNullable(result.getBlockNumber()).isPresent()) {
            return "从" + result.getFrom() + "账户转账到" + result.getTo() + "账户 " + result.getValue() + " 转账状态为pending";
        }
        TransactionReceipt.ResultBean receipt = getTransactionReceipt(result.getHash()).getResult();
        if (Optional.ofNullable(receipt).isPresent()) {
            String status = Integer.parseInt(receipt.getStatus().substring(2), 16) == 1 ? "success" : "failure";
            return "从" + result.getFrom() + "账户转账到" + result.getTo() + "账户 " + result.getValue() + " 转账" + status;
        }
        return null;
    }


}
