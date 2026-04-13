package com.example.reportmanagementapp.domain.repository;

import com.example.reportmanagementapp.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
