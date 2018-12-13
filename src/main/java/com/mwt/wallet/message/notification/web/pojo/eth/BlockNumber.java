package com.mwt.wallet.message.notification.web.pojo.eth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockNumber {
    private int id;
    private String jsonrpc;
    private String result;
}
