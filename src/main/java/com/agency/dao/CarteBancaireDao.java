package com.agency.dao;

import com.agency.model.CarteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteBancaireDao extends JpaRepository<CarteBancaire, Long> {
}
