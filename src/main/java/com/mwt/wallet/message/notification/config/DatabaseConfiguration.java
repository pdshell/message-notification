package com.mwt.wallet.message.notification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableRedisRepositories("com.mwt.wallet.message.notification.repository.redis")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
