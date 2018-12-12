package com.mwt.wallet.message.notification.web;

import com.mwt.wallet.message.notification.web.pojo.eth.AddressHash;
import com.mwt.wallet.message.notification.service.MessagesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequestMapping("api/mobile")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @PostMapping("/ethMessageNotification")
    @ApiOperation("获取eth的消息通知")
    public ResponseEntity ethMessageNotification(@RequestBody AddressHash addressHash) {
        return ResponseEntity.ok(Collections.singletonMap("message", messagesService.ethMessageNotification(addressHash)));
    }

    @GetMapping("/getTransactionByHash")
    @ApiOperation("获取eth的交易详情")
    public ResponseEntity getTransactionByHash(String hash) {
        return ResponseEntity.ok(messagesService.getTransactionByHash(hash));
    }

    @GetMapping("/getTransactionReceipt")
    @ApiOperation("是否生成交易记录以及成功或失败")
    public ResponseEntity getTransactionReceipt(String hash) {
        return ResponseEntity.ok(messagesService.getTransactionReceipt(hash));
    }


}
