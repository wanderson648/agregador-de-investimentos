package com.wo.agregadordeinvestimentos.repository;

import com.wo.agregadordeinvestimentos.entity.BillingAddress;
import com.wo.agregadordeinvestimentos.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}
