package com.mwt.wallet.message.notification.util;

import java.math.BigDecimal;

public class StringUtils {

    private final static String length = "1000000000000000000";

    public static String ethBalanceConvert(String source) {
        BigDecimal b = new BigDecimal(source);
        String c = b.divide(new BigDecimal(length), 4, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
        return c + " ETH";
    }

}
