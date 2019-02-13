package com.mwt.wallet.message.notification.web.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ClickMessageNotificationVM {
    @NotNull
    private String addr;
    @NotNull
    private String trxId;
}
