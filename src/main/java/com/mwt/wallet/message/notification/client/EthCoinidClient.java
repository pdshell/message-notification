package com.mwt.wallet.message.notification.client;

import com.mwt.wallet.message.notification.web.pojo.CoinIdVM;
import com.mwt.wallet.message.notification.web.pojo.eth.*;
import com.mwt.wallet.retrofit.client.starter.BaseURL;
import com.mwt.wallet.retrofit.client.starter.Result;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@BaseURL("application.eth.eth-coinid")
public interface EthCoinidClient {

    @POST("/")
    Result<Object> ethChainInfo(@Body CoinIdVM coinIdVM);

    @Headers("Content-Type:application/json")
    @BaseURL("https://jsonrpc.medishares.net")
    @POST("/")
    Result<TransactionReceipt> coinIdTransactionReceipt(@Body CoinIdVM coinIdVM);

    @Headers("Content-Type:application/json")
    @BaseURL("https://jsonrpc.medishares.net")
    @POST("/")
    Result<TransactionInfo> coinIdTransactionByHash(@Body CoinIdVM coinIdVM);

    @POST("/")
    Result<BlockNumber> coinIdBlockNumber(@Body CoinIdVM coinIdVM);


}
