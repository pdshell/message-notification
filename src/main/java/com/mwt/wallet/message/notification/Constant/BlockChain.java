package com.mwt.wallet.message.notification.Constant;

/**
 * created by shangzebei 2018/10/23
 */
public enum BlockChain {
    ETH("eth"),
    BTC("btc"),
    EOS("eos"),
    VNS("vns");
    String name;

    BlockChain(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
