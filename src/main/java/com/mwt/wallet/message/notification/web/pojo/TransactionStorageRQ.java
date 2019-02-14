package com.mwt.wallet.message.notification.web.pojo;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@RedisHash(value = "TransactionStorage")
@Data
public class TransactionStorageRQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Indexed
    private String trxId;

    @NotNull
    @Indexed
    private String type;
    @Indexed
    private String from;
    @Indexed
    private String to;
    private Long createTime;
    @Indexed
    private int status;
//    @Indexed
    private int state;
}
