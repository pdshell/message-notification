package com.mwt.wallet.message.notification.repository.redis;

import com.mwt.wallet.message.notification.web.pojo.TransactionStorageRQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionStorageRepository extends CrudRepository<TransactionStorageRQ, Long> {

    Page<TransactionStorageRQ> findByTypeContainingAndStatusNotAndFromOrTo(String type, Integer status, String from, String to, Pageable pageable);

    List<TransactionStorageRQ> findAllByTypeAndStatusAndFromOrTo(String type, Integer status, String from, String to);

    Optional<TransactionStorageRQ> findByTrxId(String trxId);

    List<TransactionStorageRQ> findAllByStatus(Integer status);
}
