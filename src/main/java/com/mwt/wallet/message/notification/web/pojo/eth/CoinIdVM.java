package com.mwt.wallet.message.notification.web.pojo.eth;

import lombok.Data;

import java.util.List;

@Data
public class CoinIdVM {
    private Integer id = 3;
    private String jsonrpc = "2.0";
    private String method;
    private List<?> params;
}
