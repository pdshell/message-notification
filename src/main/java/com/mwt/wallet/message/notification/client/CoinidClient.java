package com.mwt.wallet.message.notification.client;

import com.mwt.wallet.message.notification.web.pojo.eth.CoinIdVM;
import com.mwt.wallet.message.notification.web.pojo.eth.TransactionInfo;
import com.mwt.wallet.message.notification.web.pojo.eth.TransactionReceipt;
import com.mwt.wallet.retrofitclientstarter.retrofit.BaseURL;
import com.mwt.wallet.retrofitclientstarter.retrofit.Result;
import retrofit2.http.Body;
import retrofit2.http.POST;

@BaseURL("application.eth.eth-coinid")
public interface CoinidClient {

    @POST("/")
    Result<Object> getCoinId(@Body CoinIdVM coinIdVM);

    @POST("/")
    Result<TransactionReceipt> coinIdTransactionReceipt(@Body CoinIdVM coinIdVM);

    @POST("/")
    Result<TransactionInfo> coinIdTransactionByHash(@Body CoinIdVM coinIdVM);


}
