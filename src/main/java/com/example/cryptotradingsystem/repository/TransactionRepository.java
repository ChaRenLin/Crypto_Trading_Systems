package com.example.cryptotradingsystem.repository;

import com.example.cryptotradingsystem.model.TransactionPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionPO, Long> {
   List<TransactionPO> findByUserPO_Username(String username);
}