package com.mwt.wallet.message.notification.repository;

import com.mwt.wallet.message.notification.domain.MessageNotificationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageNotificationRecordRepository extends JpaRepository<MessageNotificationRecord,Long> {

}
