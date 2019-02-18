package com.mwt.wallet.message.notification;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableSwaggerBootstrapUI
@EnableScheduling
public class MessageNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageNotificationApplication.class, args);
    }
}
