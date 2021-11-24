package com.agency.dao;

import com.agency.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionDao extends JpaRepository<Question, Long> {

}
