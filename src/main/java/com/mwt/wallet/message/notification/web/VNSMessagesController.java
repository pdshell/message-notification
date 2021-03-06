package com.mwt.wallet.message.notification.web;

import com.mwt.wallet.message.notification.service.VNSMessagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mobile/vns")
@Api(description = "vns", tags = "获取VNS相关数据")
public class VNSMessagesController {

    @Autowired
    private VNSMessagesService vnsMessagesService;

    @GetMapping("/getTransactionByHash")
    @ApiOperation("获取eth的交易详情")
    public ResponseEntity getTransactionByHash(String hash) {
        return ResponseEntity.ok(vnsMessagesService.getTransactionByHash(hash));
    }

    @GetMapping("/blockNumber")
    @ApiOperation("获取区块数量")
    public ResponseEntity blockNumber() {
        return ResponseEntity.ok(vnsMessagesService.blockNumber());
    }

    @GetMapping("/getBalance")
    @ApiOperation("获取用户的余额")
    public ResponseEntity getBalance(String account, String blockStatus) {
        return ResponseEntity.ok(vnsMessagesService.getBalance(account, blockStatus));
    }

    @GetMapping("/getTransactionByBlockNumberAndIndex")
    @ApiOperation("通过区块高度和第几笔交易获取交易记录")
    public ResponseEntity getTransactionByBlockNumberAndIndex(String blockNumber, String index) {
        return ResponseEntity.ok(vnsMessagesService.getTransactionByBlockNumberAndIndex(blockNumber, index));
    }

    @GetMapping("/getTransactionReceipt")
    @ApiOperation("是否生成交易记录以及成功或失败")
    public ResponseEntity getTransactionReceipt(String hash) {
        return ResponseEntity.ok(vnsMessagesService.getTransactionReceipt(hash));
    }
}
