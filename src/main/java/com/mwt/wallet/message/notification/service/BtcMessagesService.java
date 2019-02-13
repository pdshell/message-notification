package com.mwt.wallet.message.notification.service;

import com.mwt.wallet.message.notification.Constant.BTCConstant;
import com.mwt.wallet.message.notification.client.BtcCoinidClient;
import com.mwt.wallet.message.notification.util.StringUtils;
import com.mwt.wallet.message.notification.web.pojo.CoinIdVM;
import com.mwt.wallet.message.notification.web.pojo.btc.BlockChainRQ;
import com.mwt.wallet.message.notification.web.pojo.btc.TransactionRQ;
import com.mwt.wallet.message.notification.web.pojo.btc.TxHashRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BtcMessagesService {

    @Autowired
    private BtcCoinidClient btcCoinidClient;

    public BlockChainRQ blockChainInfo() {
        CoinIdVM coinId = StringUtils.getParameter(null, BTCConstant.METHOD_BLOCKINFO.getName());
        return btcCoinidClient.btcChainInfo(coinId).getResult();
    }

    public Object getBlockHash(Integer blockHeight) {
        List<Object> param = new ArrayList<>();
        param.add(blockHeight);
        CoinIdVM coinId = StringUtils.getParameter(param, BTCConstant.METHOD_BLOCKHASH.getName());
        return btcCoinidClient.getBlockHash(coinId).getResult();
    }

    public TransactionRQ getTransactionByTxHash(String txHash, boolean verbose) {
        List<Object> param = new ArrayList<>();
        param.add(txHash);
        param.add(verbose);
        CoinIdVM coinId = StringUtils.getParameter(param, BTCConstant.METHOD_TRANSACTION.getName());
        return btcCoinidClient.getTransactionByTxHash(coinId).getResult();
    }

    public TxHashRQ getTransactionHash(String scriptHash) {
        List<Object> param = new ArrayList<>();
        param.add(scriptHash);
        CoinIdVM coinId = StringUtils.getParameter(param, BTCConstant.METHOD_HISTORY.getName());
        return btcCoinidClient.getTransactionHash(coinId).getResult();
    }

    public Object getBalance(String scriptHash) {
        List<Object> param = new ArrayList<>();
        param.add(scriptHash);
        CoinIdVM coinId = StringUtils.getParameter(param, BTCConstant.METHOD_BALANCE.getName());
        return btcCoinidClient.getBlockHash(coinId).getResult();
    }

    public Object getMempool(String scriptHash){
        List<Object> param = new ArrayList<>();
        param.add(scriptHash);
        CoinIdVM coinId = StringUtils.getParameter(param, BTCConstant.METHOD_MEMPOOL.getName());
        return btcCoinidClient.getBlockHash(coinId).getResult();
    }


}
