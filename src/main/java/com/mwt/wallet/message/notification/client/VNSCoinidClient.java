package com.mwt.wallet.message.notification.client;

import com.mwt.wallet.message.notification.web.pojo.CoinIdVM;
import com.mwt.wallet.message.notification.web.pojo.eth.BlockNumber;
import com.mwt.wallet.message.notification.web.pojo.eth.TransactionInfo;
import com.mwt.wallet.message.notification.web.pojo.eth.TransactionReceipt;
import com.mwt.wallet.retrofit.client.starter.BaseURL;
import com.mwt.wallet.retrofit.client.starter.Result;
import retrofit2.http.Body;
import retrofit2.http.POST;

@BaseURL("application.vns.vns-coinid")
public interface VNSCoinidClient {

    @POST("/")
    Result<Object> ethChainInfo(@Body CoinIdVM coinIdVM);

    @POST("/")
    Result<TransactionReceipt> coinIdTransactionReceipt(@Body CoinIdVM coinIdVM);

    @POST("/")
    Result<TransactionInfo> coinIdTransactionByHash(@Body CoinIdVM coinIdVM);

    @POST("/")
    Result<BlockNumber> coinIdBlockNumber(@Body CoinIdVM coinIdVM);
}
