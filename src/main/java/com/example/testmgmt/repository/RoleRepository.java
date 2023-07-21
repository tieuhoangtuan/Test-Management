package com.example.testmgmt.repository;

import com.example.testmgmt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRole(String role);
}
