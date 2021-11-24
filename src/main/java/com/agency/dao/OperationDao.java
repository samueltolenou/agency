package com.agency.dao;

import com.agency.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationDao extends JpaRepository<Operation, Long> {
}
