package com.mwt.wallet.message.notification.web.pojo.btc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRQ {
    private String jsonrpc;
    private int id;
    private ResultBean result;

    @Data
    public static class ResultBean {
        private String txid;
        private String hash;
        private int version;
        private int size;
        private int vsize;
        private int weight;
        private int locktime;
        private String hex;
        private String blockhash;
        private int confirmations;
        private int time;
        private int blocktime;
        private List<VinBean> vin;
        private List<VoutBean> vout;

        @Data
        public static class VinBean {
            private String txid;
            private int vout;
            private ScriptSigBean scriptSig;
            private long sequence;
            private List<String> txinwitness;

            @Data
            public static class ScriptSigBean {
                private String asm;
                private String hex;
            }
        }

        @Data
        public static class VoutBean {
            private double value;
            private int n;
            private ScriptPubKeyBean scriptPubKey;

            @Data
            public static class ScriptPubKeyBean {
                private String asm;
                private String hex;
                private int reqSigs;
                private String type;
                private List<String> addresses;
            }
        }
    }
}
