package com.mwt.wallet.message.notification.repository.redis;

import com.mwt.wallet.message.notification.Constant.BlockChain;
import com.mwt.wallet.message.notification.web.pojo.TransactionStorageRQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionStorageRepository extends CrudRepository<TransactionStorageRQ, Long> {
    Page<TransactionStorageRQ> findByTypeAndFromOrTo(BlockChain type, String from, String to, Pageable pageable);

    Optional<TransactionStorageRQ> findByTrxId(String trxId);
}
