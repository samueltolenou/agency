package com.agency.dao;

import com.agency.model.Operation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationDao extends JpaRepository<Operation, Long> {
	
	List<Operation> findAllByCompteDebiteur_NumCompteOrCompteCrediteur_NumCompte(String num1, String num2) ;
	
	
	
	
	
}
