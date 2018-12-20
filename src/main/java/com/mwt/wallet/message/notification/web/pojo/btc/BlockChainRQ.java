package com.mwt.wallet.message.notification.web.pojo.btc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockChainRQ {
    private String jsonrpc;
    private int id;
    private List<ResultBean> result;

    @Data
    public static class ResultBean {
        private String chain;
        private int blocks;
        private int headers;
        private String bestblockhash;
        private double difficulty;
        private int mediantime;
        private double verificationprogress;
        private boolean initialblockdownload;
        private String chainwork;
        private long size_on_disk;
        private boolean pruned;
        private Bip9SoftforksBean bip9_softforks;
        private String warnings;
        private int size;
        private int bytes;
        private int usage;
        private int maxmempool;
        private double mempoolminfee;
        private double minrelaytxfee;
        private List<SoftforksBean> softforks;

        @Data
        public static class Bip9SoftforksBean {
            private CsvBean csv;
            private SegwitBean segwit;

            @Data
            public static class CsvBean {
                private String status;
                private int startTime;
                private int timeout;
                private int since;
            }

            @Data
            public static class SegwitBean {
                private String status;
                private int startTime;
                private int timeout;
                private int since;
            }
        }

        @Data
        public static class SoftforksBean {
            private String id;
            private int version;
            private RejectBean reject;

            @Data
            public static class RejectBean {
                private boolean status;
            }
        }
    }
}
