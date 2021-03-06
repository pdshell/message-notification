package com.mwt.wallet.message.notification.service;

import com.mwt.wallet.message.notification.Constant.BlockChain;
import com.mwt.wallet.message.notification.repository.redis.TransactionStorageRepository;
import com.mwt.wallet.message.notification.web.pojo.ClickMessageNotificationVM;
import com.mwt.wallet.message.notification.web.pojo.TransactionStorageRQ;
import com.mwt.wallet.message.notification.web.pojo.eth.NotificationRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MessageNotificationService {

    @Autowired
    private EthMessagesService ethMessagesService;

    @Autowired
    private VNSMessagesService vnsMessagesService;

    @Autowired
    private BtcMessagesService btcMessagesService;

    @Autowired
    private TransactionStorageRepository transactionStorageRepository;

    public List<NotificationRQ> getMessageNotification(BlockChain blockChain, String addr) {
        List<NotificationRQ> notificationRQS = null;
        switch (blockChain) {
            case ETH:
                notificationRQS = ethMessagesService.ethMessageNotification(addr);
                break;
            case BTC:
                notificationRQS = btcMessagesService.btcMessageNotification(addr);
                break;
            case VNS:
                notificationRQS = vnsMessagesService.vnsMessageNotification(addr);
                break;
        }
        return notificationRQS;
    }

    public List<NotificationRQ> getMessageNotificationList(BlockChain blockChain, String addr, Integer start, Integer limit) {
        List<NotificationRQ> notificationRQS = null;
        switch (blockChain) {
            case ETH:
                notificationRQS = ethMessagesService.ethMessageNotificationList(addr, start, limit);
                break;
            case BTC:
                notificationRQS = btcMessagesService.btcMessageNotificationList(addr, start, limit);
                break;
            case VNS:
                notificationRQS = vnsMessagesService.vnsMessageNotificationList(addr, start, limit);
                break;
        }
        return notificationRQS;
    }

    //1 from已读 2 to已读 3 都已读
    public boolean clickMessageNotification(ClickMessageNotificationVM clickMessageNotificationVM) {
        if (Optional.ofNullable(clickMessageNotificationVM.getTrxId()).isPresent()) {
            transactionStorageRepository.findByStateAndFromOrTo(0, clickMessageNotificationVM.getAddr(), clickMessageNotificationVM.getAddr())
                    .forEach(transactionStorageRQ ->
                            updateTransactionState(transactionStorageRQ, clickMessageNotificationVM.getAddr()));
        } else {
            transactionStorageRepository.findByTrxId(clickMessageNotificationVM.getTrxId()).
                    ifPresent(transactionStorageRQ ->
                            updateTransactionState(transactionStorageRQ, clickMessageNotificationVM.getAddr()));
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTransactionState(TransactionStorageRQ transactionStorageRQ, String addr) {
        if (transactionStorageRQ.getFrom().equals(addr)) {
            switch (transactionStorageRQ.getState()) {
                case 0:
                    transactionStorageRQ.setState(1);
                    transactionStorageRepository.save(transactionStorageRQ);
                    break;
                case 1:
                    break;
                case 2:
                    transactionStorageRQ.setState(3);
                    transactionStorageRepository.save(transactionStorageRQ);
                    break;
            }
        } else {
            switch (transactionStorageRQ.getState()) {
                case 0:
                    transactionStorageRQ.setState(2);
                    transactionStorageRepository.save(transactionStorageRQ);
                    break;
                case 1:
                    transactionStorageRQ.setState(3);
                    transactionStorageRepository.save(transactionStorageRQ);
                    break;
                case 2:
                    break;
            }
        }
    }
}
