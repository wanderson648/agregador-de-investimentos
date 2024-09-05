package com.wo.agregadordeinvestimentos.repository;

import com.wo.agregadordeinvestimentos.entity.Account;
import com.wo.agregadordeinvestimentos.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
