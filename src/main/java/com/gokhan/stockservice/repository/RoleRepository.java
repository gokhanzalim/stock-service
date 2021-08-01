package com.gokhan.stockservice.repository;

import com.gokhan.stockservice.model.entity.Role;
import com.gokhan.stockservice.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}