package com.mwt.wallet.message.notification.Constant;

public enum VNSConstant {

    METHOD_TRANSACTIONRECEIPT("vns_getTransactionReceipt"),
    METHOD_TRANSACTIONBYHASH("vns_getTransactionByHash"),
    METHOD_BLOCKNUMBER("vns_blockNumber"),
    METHOD_BALANCE("vns_getBalance"),
    METHOD_TRANSACTIONBYBLOCKNUMBERANDINDEX("vns_getTransactionByBlockNumberAndIndex"),
    JSON_RPC("2.0"),
    ID("1");
    private String name;

    VNSConstant(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
