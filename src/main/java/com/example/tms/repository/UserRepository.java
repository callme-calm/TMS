package com.example.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.tms.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
