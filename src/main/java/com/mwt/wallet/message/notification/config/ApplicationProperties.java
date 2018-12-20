package com.mwt.wallet.message.notification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Data
public class ApplicationProperties {

    public ETH eth;

    public BTC btc;

    @Data
    public static class ETH {
        String ethCoinid = "";
    }

    @Data
    public static class BTC {
        String btcCoinid = "";
    }


}
