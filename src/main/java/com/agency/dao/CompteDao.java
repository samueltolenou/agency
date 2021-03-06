package com.agency.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.agency.model.Compte;
import org.springframework.data.jpa.repository.Query;

public interface CompteDao extends JpaRepository<Compte, Long> {

	Compte findFirstByNumCompte(String numero);

    Compte findByNumCompteIs(String num) ;
    //public Compte findCpteByNumCpte(Long num_compte);
}
