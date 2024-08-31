package com.example.cryptotradingsystem.repository;

import com.example.cryptotradingsystem.model.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserPO, Long> {
	
	Optional<UserPO> findByUsername(String name);

}
