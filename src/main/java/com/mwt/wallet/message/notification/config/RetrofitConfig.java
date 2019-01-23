package com.mwt.wallet.message.notification.config;

import com.mwt.wallet.retrofit.client.starter.EnableRetrofitClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRetrofitClients(basePackages = {"com.mwt.wallet.message.notification"})
public class RetrofitConfig {

}
