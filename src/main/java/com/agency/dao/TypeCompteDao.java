package com.agency.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agency.model.TypeCompte;

public interface TypeCompteDao extends JpaRepository<TypeCompte, Long> {
}
