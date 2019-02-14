package com.mwt.wallet.message.notification.web;

import com.mwt.wallet.message.notification.service.BtcMessagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mobile/btc")
@Api(description = "btc", tags = "获取BTC相关数据")
public class BtcMessagesController {

    @Autowired
    private BtcMessagesService btcMessagesService;

    @GetMapping("/blockChainInfo")
    @ApiOperation(value = "取当前块链信息")
    public ResponseEntity blockChainInfo() {
        return ResponseEntity.ok(btcMessagesService.blockChainInfo());
    }

    @GetMapping("/getBlockHash")
    @ApiOperation(value = "区块高度获取区块HASH")
    public ResponseEntity getBlockHash(Integer blockHeight) {
        return ResponseEntity.ok(btcMessagesService.getBlockHash(blockHeight));
    }

    @GetMapping("/getTransactionByHash")
    @ApiOperation(value = "获取交易记录通过hash")
    public ResponseEntity getTransactionByHash(String txHash, boolean verbose) {
        return ResponseEntity.ok(btcMessagesService.getTransactionByTxHash(txHash, verbose));
    }

    @GetMapping("/getTransactionHash")
    @ApiOperation(value = "获取交易记录")
    public ResponseEntity getTransactionHash(String scriptHash) {
        return ResponseEntity.ok(btcMessagesService.getTransactionByHash(scriptHash));
    }

    @GetMapping("/getBalance")
    @ApiOperation(value = "获取余额")
    public ResponseEntity getBalance(String scriptHash) {
        return ResponseEntity.ok(btcMessagesService.getBalance(scriptHash));
    }

    @GetMapping("/getMempool")
    @ApiOperation(value = "获取当前交易记录")
    public ResponseEntity getMempool(String scriptHash) {
        return ResponseEntity.ok(btcMessagesService.getMempool(scriptHash));
    }

    @GetMapping("/getTransactionByTxHash")
    @ApiOperation(value = "获取交易数据")
    public ResponseEntity getTransactionByTxHash(String txHash) {
        return ResponseEntity.ok(btcMessagesService.getTransactionByTxHash(txHash, true));
    }


}
