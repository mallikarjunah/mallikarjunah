package com.tech.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.model.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findByAccountNumberAndPin(Long accountNumber, Integer pin);
}
