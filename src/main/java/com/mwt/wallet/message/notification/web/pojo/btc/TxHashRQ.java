package com.mwt.wallet.message.notification.web.pojo.btc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TxHashRQ {
    private String jsonrpc;
    private int id;
    private List<ResultBean> result;

    @Data
    public static class ResultBean {
        private String tx_hash;
        private int height;
    }
}
