package com.mwt.wallet.message.notification.web.pojo.eth;

import com.mwt.wallet.message.notification.Constant.TransactionStateConstant;
import lombok.Data;

@Data
public class NotificationRQ {
    private String from;
    private String to;
    private TransactionStateConstant state;
    private String value;

}
