package com.agency.dao;

import com.agency.model.Conseiller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConseillerDao extends JpaRepository<Conseiller, Long> {

	List<Conseiller> findByNomContaining(String nom);

	boolean existsByNom(String nom);

	Conseiller findByNom(String nom);
}
