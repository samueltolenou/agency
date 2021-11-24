package com.agency.dao;

import com.agency.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientDao extends JpaRepository<Client, Long> {

	Client  findByIdClient(Long id);

	java.util.List<Client> findByNomContaining(String nom);


}
