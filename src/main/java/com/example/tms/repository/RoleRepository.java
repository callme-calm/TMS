package com.example.tms.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.tms.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
