package com.agency.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.agency.model.Compte;

public interface CompteDao extends JpaRepository<Compte, Long> {

	Compte findFirstByNumCompte(String numero);
  //public Compte findCpteByNumCpte(Long num_compte);
}
