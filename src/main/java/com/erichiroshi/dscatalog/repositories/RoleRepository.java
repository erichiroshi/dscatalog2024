package com.erichiroshi.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erichiroshi.dscatalog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}