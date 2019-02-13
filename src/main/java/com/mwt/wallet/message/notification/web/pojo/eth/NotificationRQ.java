package com.mwt.wallet.message.notification.web.pojo.eth;

import com.mwt.wallet.message.notification.Constant.TransactionStateConstant;
import lombok.Data;

@Data
public class NotificationRQ {
    private String trxId;
    private String from;
    private String to;
    private TransactionStateConstant trxState;
    private String gasPrice;
    private String gasLimit;
    private String blHeight;
    private String value;
    private String createTime;
}
