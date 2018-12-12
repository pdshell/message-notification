package com.mwt.wallet.message.notification.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "message_notification_record")
@Data
/**
 * 消息通知记录
 */
public class MessageNotificationRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hash")
    private String hash;

    @Column(name = "message")
    private String message;

    @Column(name = "create_time")
    private Long createTime;

}
