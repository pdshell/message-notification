package com.mwt.wallet.message.notification.Constant;

public enum  ETHConstant {

    METHOD_TRANSACTIONRECEIPT("eth_getTransactionReceipt"),
    METHOD_TRANSACTIONBYHASH("eth_getTransactionByHash"),
    JSON_RPC("2.0"),
    ID("1");
    private String name;

    ETHConstant(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
