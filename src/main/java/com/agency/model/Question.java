package com.agency.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class Question implements Serializable {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String libelleQuestion;


    public Question() {
        super();
    }

    public Question(String libelleQuestion) {
        this.libelleQuestion = libelleQuestion;
    }

  
}
