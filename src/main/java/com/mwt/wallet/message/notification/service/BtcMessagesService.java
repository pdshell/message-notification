package com.mwt.wallet.message.notification.service;

import com.mwt.wallet.message.notification.Constant.BTCConstant;
import com.mwt.wallet.message.notification.Constant.BlockChain;
import com.mwt.wallet.message.notification.client.BtcCoinidClient;
import com.mwt.wallet.message.notification.repository.redis.TransactionStorageRepository;
import com.mwt.wallet.message.notification.util.DateUtil;
import com.mwt.wallet.message.notification.util.StringUtils;
import com.mwt.wallet.message.notification.web.pojo.CoinIdVM;
import com.mwt.wallet.message.notification.web.pojo.TransactionStorageRQ;
import com.mwt.wallet.message.notification.web.pojo.btc.BlockChainRQ;
import com.mwt.wallet.message.notification.web.pojo.btc.TransactionRQ;
import com.mwt.wallet.message.notification.web.pojo.btc.TxHashRQ;
import com.mwt.wallet.message.notification.web.pojo.eth.NotificationRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BtcMessagesService {

    @Autowired
    private BtcCoinidClient btcCoinidClient;

    @Autowired
    private TransactionStorageRepository transactionStorageRepository;

    @Autowired
    private EthMessagesService ethMessagesService;

    public BlockChainRQ blockChainInfo() {
        CoinIdVM coinId = StringUtils.getParameter(null, BTCConstant.METHOD_BLOCKINFO.getName());
        return btcCoinidClient.btcChainInfo(coinId).getResult();
    }

    public Object getBlockHash(Integer blockHeight) {
        List<Object> param = new ArrayList<>();
        param.add(blockHeight);
        CoinIdVM coinId = StringUtils.getParameter(param, BTCConstant.METHOD_BLOCKHASH.getName());
        return btcCoinidClient.getBlockHash(coinId).getResult();
    }

    public TransactionRQ getTransactionByTxHash(String txHash, boolean verbose) {
        List<Object> param = new ArrayList<>();
        param.add(txHash);
        param.add(verbose);
        CoinIdVM coinId = StringUtils.getParameter(param, BTCConstant.METHOD_TRANSACTION.getName());
        return btcCoinidClient.getTransactionByTxHash(coinId).getResult();
    }

    public TxHashRQ getTransactionByHash(String scriptHash) {
        List<Object> param = new ArrayList<>();
        param.add(scriptHash);
        CoinIdVM coinId = StringUtils.getParameter(param, BTCConstant.METHOD_HISTORY.getName());
        return btcCoinidClient.getTransactionHash(coinId).getResult();
    }

    public Object getBalance(String scriptHash) {
        List<Object> param = new ArrayList<>();
        param.add(scriptHash);
        CoinIdVM coinId = StringUtils.getParameter(param, BTCConstant.METHOD_BALANCE.getName());
        return btcCoinidClient.getBlockHash(coinId).getResult();
    }

    public Object getMempool(String scriptHash) {
        List<Object> param = new ArrayList<>();
        param.add(scriptHash);
        CoinIdVM coinId = StringUtils.getParameter(param, BTCConstant.METHOD_MEMPOOL.getName());
        return btcCoinidClient.getBlockHash(coinId).getResult();
    }

    //0 未通知(节点未确认) 1 通知 2 已通知
    public List<NotificationRQ> btcMessageNotification(String addr, Integer start, Integer limit) {
        Pageable pageable = PageRequest.of(start > 0 ? start - 1 : 0, limit, new Sort(Sort.Direction.DESC, "createTime"));
        Page<TransactionStorageRQ> transactionStorageRQPage = transactionStorageRepository.findByTypeAndFromOrTo(BlockChain.BTC, addr, addr, pageable);
        List<NotificationRQ> notificationRQS = new ArrayList<>();
        List<TxHashRQ.ResultBean> resultBeanList = getTransactionByHash(addr).getResult();
        if (resultBeanList.size() != 0 && transactionStorageRQPage.getSize() != 0) {
            transactionStorageRQPage.forEach(transactionStorageRQ -> resultBeanList.forEach(resultBean -> {
                if (transactionStorageRQ.getTrxId().equals(resultBean.getTx_hash())) {
                    TransactionRQ.ResultBean transactionRQ = getTransactionByTxHash(resultBean.getTx_hash(), true).getResult();
                    NotificationRQ notificationRQ = new NotificationRQ();
                    notificationRQ.setValue(transactionRQ.getVout().get(0).getValue()+" BTC");
//                    notificationRQ.setTxFee();
                    notificationRQ.setTimeStamp(DateUtil.longToString(transactionRQ.getTime() * 1000));
                    notificationRQ.setTrxId(transactionStorageRQ.getTrxId());
                    notificationRQ.setBlHeight(resultBean.getHeight() + "");
                    notificationRQ.setFrom(transactionStorageRQ.getFrom());
                    notificationRQ.setTo(transactionStorageRQ.getTo());
                    notificationRQ.setCreateTime(DateUtil.longToString(transactionStorageRQ.getCreateTime()));
                    notificationRQS.add(notificationRQ);
                    ethMessagesService.updateTransactionStorage(transactionStorageRQ);
                }
            }));
        }
        return notificationRQS;
    }


}
