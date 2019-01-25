package com.mwt.wallet.message.notification.web;

import com.mwt.wallet.message.notification.Constant.BlockChain;
import com.mwt.wallet.message.notification.service.MessageNotificationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mobile")
public class MessageNotificationController {

    @Autowired
    private MessageNotificationService messageNotificationService;

    @GetMapping("/getMessageNotification")
    @ApiOperation("获取eth，btc，vns的消息通知")
    public ResponseEntity getMessageNotification(String trxId, BlockChain blockChain) {
        return ResponseEntity.ok(messageNotificationService.getMessageNotification(trxId, blockChain));
    }
}
