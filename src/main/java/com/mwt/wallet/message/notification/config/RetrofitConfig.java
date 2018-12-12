package com.mwt.wallet.message.notification.config;

import com.mwt.wallet.retrofitclientstarter.retrofit.EnableRetrofitClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRetrofitClients(basePackages = {"com.mwt.wallet.message.notification"})
public class RetrofitConfig {

}
