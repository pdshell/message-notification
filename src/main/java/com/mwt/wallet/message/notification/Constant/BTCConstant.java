package com.mwt.wallet.message.notification.Constant;

public enum BTCConstant {

    METHOD_BLOCKINFO("blockchain.info"),
    METHOD_BALANCE("blockchain.scripthash.get_balance"),
    METHOD_HISTORY("blockchain.scripthash.get_history"),
    METHOD_UNSPENT("blockchain.scripthash.listunspent"),
    METHOD_BROADCAST("blockchain.transaction.broadcast"),
    METHOD_MEMPOOL("blockchain.scripthash.get_mempool"),
    METHOD_TRANSACTION("blockchain.transaction.get"),
    METHOD_BLOCKHASH("getblockhash");
    private String name;

    BTCConstant(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
