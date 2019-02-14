package com.mwt.wallet.message.notification.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static long getLong(String data) throws ParseException {
        return sdf_1.parse(data).getTime();
    }

    public static String longToString(long seconds) {
        Date date = new Date(seconds);

        return sdf_1.format(date);
    }


}
