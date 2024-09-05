package com.wo.agregadordeinvestimentos.repository;

import com.wo.agregadordeinvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
