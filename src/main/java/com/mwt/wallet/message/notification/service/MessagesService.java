package com.mwt.wallet.message.notification.service;

import com.mwt.wallet.message.notification.Constant.ETHConstant;
import com.mwt.wallet.message.notification.client.CoinidClient;
import com.mwt.wallet.message.notification.domain.MessageNotificationRecord;
import com.mwt.wallet.message.notification.repository.MessageNotificationRecordRepository;
import com.mwt.wallet.message.notification.web.pojo.eth.*;
import com.mwt.wallet.message.notification.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessagesService {

    @Autowired
    private CoinidClient coinidClient;

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

    public TransactionInfo getTransactionByBlockNumberAndIndex(String blockNumber, String index) {
        CoinIdVM coinId = new CoinIdVM();
        List<String> param = new ArrayList<>();
        param.add(blockNumber);
        param.add(index);
        coinId.setJsonrpc(ETHConstant.JSON_RPC.getName());
        coinId.setMethod(ETHConstant.METHOD_TRANSACTIONBYBLOCKNUMBERANDINDEX.getName());
        coinId.setId(Integer.parseInt(ETHConstant.ID.getName()));
        coinId.setParams(param);
        return coinidClient.coinIdTransactionByHash(coinId).getResult();
    }


    public BlockNumber blockNumber() {
        CoinIdVM coinId = new CoinIdVM();
        coinId.setJsonrpc(ETHConstant.JSON_RPC.getName());
        coinId.setMethod(ETHConstant.METHOD_BLOCKNUMBER.getName());
        coinId.setId(Integer.parseInt(ETHConstant.ID.getName()));
        coinId.setParams(new ArrayList<>());
        return coinidClient.coinIdBlockNumber(coinId).getResult();
    }

    public List<String> ethMessageNotification() {
        BlockNumber blockNumber = blockNumber();
        long index = 0L;
        List<String> messages = new ArrayList<>();
        while (true) {
            TransactionInfo.ResultBean resultBean = getTransactionByBlockNumberAndIndex(blockNumber.getResult(), "0x" + Long.toHexString(index)).getResult();
//            System.out.println("+++++"+resultBean.getValue());
//            String value = StringUtils.ethBalanceConvert(Long.parseLong(resultBean.getValue().substring(2), 16) + "");
            if (!Optional.ofNullable(resultBean).isPresent() && !messages.equals("[]")) {
                return messages;
            }
            TransactionReceipt.ResultBean result = getTransactionReceipt(resultBean.getHash()).getResult();
            if (Optional.ofNullable(result).isPresent() && !Optional.ofNullable(result.getRoot()).isPresent()) {
                String status = Integer.parseInt(result.getStatus().substring(2), 16) == 1 ? "success" : "failure";
                String message = "从" + resultBean.getFrom() + "账户转账到" + resultBean.getTo() + "账户 " + resultBean.getValue() + " 转账" + status;
                messages.add(message);
            }
            index++;
        }
    }


}
