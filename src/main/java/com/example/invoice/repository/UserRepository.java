package com.example.invoice.repository;

import com.example.invoice.entity.Role;
import com.example.invoice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
