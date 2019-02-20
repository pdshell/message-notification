package com.mwt.wallet.message.notification.web.pojo.eth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionInfo {
    private String jsonrpc;
    private int id;
    private ResultBean result;

    @Data
    public static class ResultBean {
        private String blockHash;
        private String blockNumber;
        private String from;
        private String gas;
        private String gasPrice;
        private String hash;
        private String input;
        private String nonce;
        private String to;
        private String transactionIndex;
        private String value;
        private String v;
        private String r;
        private String s;
        private String chainId;
        private String condition;
        private String creates;
        private String publicKey;
        private String standardV;
        private String raw;
    }
}
