package com.mwt.wallet.message.notification.service;

import com.mwt.wallet.message.notification.Constant.BlockChain;
import com.mwt.wallet.message.notification.web.pojo.eth.NotificationRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageNotificationService {

    @Autowired
    private EthMessagesService ethMessagesService;

    @Autowired
    private VNSMessagesService vnsMessagesService;

    public NotificationRQ getMessageNotification(String trxId, BlockChain blockChain) {
        NotificationRQ notification = null;
        switch (blockChain) {
            case ETH:
                notification = ethMessagesService.ethMessageNotification(trxId);
                break;
            case BTC:
                break;
            case VNS:
                notification = vnsMessagesService.vnsMessageNotification(trxId);
                break;
        }
        return notification;
    }
}
