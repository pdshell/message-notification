package com.mwt.wallet.message.notification.web.pojo.eth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionReceipt {

    private String jsonrpc;
    private int id;
    private ResultBean result;

    @Data
    public static class ResultBean {
        private String blockHash;
        private String blockNumber;
        private Object contractAddress;
        private String cumulativeGasUsed;
        private String from;
        private String gasUsed;
        private String logsBloom;
        private String status;
        private String to;
        private String transactionHash;
        private String transactionIndex;
        private List<?> logs;
        private String root;
    }
}
