package com.agency.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agency.enums.RoleName;
import com.agency.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
    boolean existsByName(RoleName roleName);
}
