package com.example.cryptotradingsystem.repository;

import com.example.cryptotradingsystem.model.CryptoPricePO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoPriceRepository extends JpaRepository<CryptoPricePO, Long> {
	CryptoPricePO findFirstBySymbolOrderByTimestampDesc(String symbol);
}
