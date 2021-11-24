package com.agency.dao;

import com.agency.model.Banque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanqueDao extends JpaRepository<Banque, Long> {
}
