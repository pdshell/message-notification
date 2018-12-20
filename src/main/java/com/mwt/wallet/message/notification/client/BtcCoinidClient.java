package com.mwt.wallet.message.notification.client;

import com.mwt.wallet.message.notification.web.pojo.CoinIdVM;
import com.mwt.wallet.message.notification.web.pojo.btc.BlockChainRQ;
import com.mwt.wallet.message.notification.web.pojo.btc.TransactionRQ;
import com.mwt.wallet.message.notification.web.pojo.btc.TxHashRQ;
import com.mwt.wallet.retrofitclientstarter.retrofit.BaseURL;
import com.mwt.wallet.retrofitclientstarter.retrofit.Result;
import retrofit2.http.Body;
import retrofit2.http.POST;

@BaseURL("application.btc.btc-coinid")
public interface BtcCoinidClient {

    @POST("/btc/api")
    Result<BlockChainRQ> btcChainInfo(@Body CoinIdVM coinIdVM);

    @POST("/btc/api")
    Result<Object> getBlockHash(@Body CoinIdVM coinIdVM);

    @POST("/btc/api")
    Result<TransactionRQ> getTransactionByTxHash(@Body CoinIdVM coinIdVM);

    @POST("/btc/api")
    Result<TxHashRQ> getTransactionHash(@Body CoinIdVM coinIdVM);

}
