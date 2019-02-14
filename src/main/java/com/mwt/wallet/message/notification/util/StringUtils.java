package com.mwt.wallet.message.notification.util;

import com.mwt.wallet.message.notification.Constant.ETHConstant;
import com.mwt.wallet.message.notification.web.pojo.CoinIdVM;

import java.math.BigDecimal;
import java.util.List;

public class StringUtils {

    private final static String length = "1000000000000000000";

    public static String ethBalanceConvert(String source) {
        BigDecimal b = new BigDecimal(source);
        String c = b.divide(new BigDecimal(length), 4, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
        return c + " ETH";
    }

    public static String btcTransactionValue(Long value) {
        Integer length = 100000000;
        BigDecimal b = new BigDecimal(value);
        String c = b.divide(new BigDecimal(length), 9, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
        return c + " BTC";
    }


    public static CoinIdVM getParameter(List<Object> params, String method) {
        CoinIdVM coinId = new CoinIdVM();
        coinId.setJsonrpc(ETHConstant.JSON_RPC.getName());
        coinId.setId(Integer.parseInt(ETHConstant.ID.getName()));
        coinId.setMethod(method);
        coinId.setParams(params);
        return coinId;
    }

}
