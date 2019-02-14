package com.mwt.wallet.message.notification.web;

import com.mwt.wallet.message.notification.Constant.BlockChain;
import com.mwt.wallet.message.notification.service.MessageNotificationService;
import com.mwt.wallet.message.notification.web.pojo.ClickMessageNotificationVM;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/mobile")
public class MessageNotificationController {

    @Autowired
    private MessageNotificationService messageNotificationService;

    @GetMapping("/getMessageNotification")
    @ApiOperation("获取eth，btc，vns的消息通知")
    public ResponseEntity getMessageNotification(BlockChain blockChain, String addr, Integer start, Integer limit) {
        return ResponseEntity.ok(messageNotificationService.getMessageNotification(blockChain, addr, start, limit));
    }

    @PostMapping("/clickMessageNotification")
    @ApiOperation("转账通知的已读和未读")
    public ResponseEntity clickMessageNotification(@Valid @RequestBody ClickMessageNotificationVM clickMessageNotificationVM) {
        return ResponseEntity.ok(messageNotificationService.clickMessageNotification(clickMessageNotificationVM));
    }

}
