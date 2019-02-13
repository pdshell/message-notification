package com.mwt.wallet.message.notification.service;

import com.mwt.wallet.message.notification.Constant.BlockChain;
import com.mwt.wallet.message.notification.repository.redis.TransactionStorageRepository;
import com.mwt.wallet.message.notification.web.pojo.ClickMessageNotificationVM;
import com.mwt.wallet.message.notification.web.pojo.eth.NotificationRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageNotificationService {

    @Autowired
    private EthMessagesService ethMessagesService;

    @Autowired
    private VNSMessagesService vnsMessagesService;

    @Autowired
    private TransactionStorageRepository transactionStorageRepository;

    public List<NotificationRQ> getMessageNotification(BlockChain blockChain, String addr) {
        List<NotificationRQ> notificationRQS = null;
        switch (blockChain) {
            case ETH:
                notificationRQS = ethMessagesService.ethMessageNotification(addr);
                break;
            case BTC:
                break;
            case VNS:
                notificationRQS = vnsMessagesService.vnsMessageNotification(addr);
                break;
        }
        return notificationRQS;
    }

    //1 from已读 2 to已读 3 都已读
    public boolean clickMessageNotification(ClickMessageNotificationVM clickMessageNotificationVM) {
        transactionStorageRepository.findByTrxId(clickMessageNotificationVM.getTrxId()).ifPresent(transactionStorageRQ -> {
            if (transactionStorageRQ.getFrom().equals(clickMessageNotificationVM.getAddr())) {
                switch (transactionStorageRQ.getState()) {
                    case 0:
                        transactionStorageRQ.setState(1);
                        break;
                    case 1:
                        break;
                    case 2:
                        transactionStorageRQ.setState(3);
                        break;
                }
            } else {
                switch (transactionStorageRQ.getState()) {
                    case 0:
                        transactionStorageRQ.setState(2);
                        break;
                    case 1:
                        transactionStorageRQ.setState(3);
                        break;
                    case 2:
                        break;
                }
            }
            transactionStorageRepository.save(transactionStorageRQ);
        });
        return true;
    }
}
