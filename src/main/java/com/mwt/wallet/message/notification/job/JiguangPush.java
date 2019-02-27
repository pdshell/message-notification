package com.mwt.wallet.message.notification.job;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * java后台极光推送方式二：使用Java SDK
 */
@SuppressWarnings({"deprecation", "restriction"})
public class JiguangPush {

    private static final Logger log = LoggerFactory.getLogger(JiguangPush.class);
    private static String masterSecret = "xxxxxxxxxxxxxxxxx";
    private static String appKey = "xxxxxxxxxxxxxxxx";
//    private static final String ALERT = "【CoinID钱包】推送信息";

    /**
     * 极光推送
     */
    public static void jiguangPush(String alias1, String alias2, String value) {
        String ALERT = "【CoinID钱包】由" + alias1 + "转账到" + alias2 + "账户的" + value + "已到账";
//        String tag = "" //声明标签
//        String alias = "123456";//声明别名
        log.info("对别名" + alias1 + " 和" + " " + alias2 + "的用户推送信息");
        PushResult result = push(alias1, alias2, ALERT);
        if (result != null && result.isResultOK()) {
            log.info("针对别名" + alias1 + " 和" + " " + alias2 + "的信息推送成功！");
        } else {
            log.info("针对别名" + alias1 + " 和" + " " + alias2 + "的信息推送失败！");
        }
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     *
     * @param alias1
     * @param alert
     * @return PushPayload
     */
    private static PushPayload buildPushObject_android_ios_alias_alert(String alias1, String alias2, String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias1, alias2))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * 极光推送方法(采用java SDK)
     *
     * @param alias1
     * @param alert
     * @return PushResult
     */
    private static PushResult push(String alias1, String alias2, String alert) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
        PushPayload payload = buildPushObject_android_ios_alias_alert(alias1, alias2, alert);
        try {
            return jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
            return null;
        }
    }
}
