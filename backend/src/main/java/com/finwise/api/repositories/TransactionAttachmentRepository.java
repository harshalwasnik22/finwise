package com.finwise.api.repositories;

import com.finwise.api.models.Transaction;
import com.finwise.api.models.TransactionAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionAttachmentRepository extends JpaRepository<TransactionAttachment, Long> {
    List<TransactionAttachment> findByTransaction(Transaction transaction);
}