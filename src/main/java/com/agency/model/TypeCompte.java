package com.agency.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class TypeCompte implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String typeCompte;
    private float tauxRemuneration;

    public TypeCompte() {
        super();
    }

    public TypeCompte(String typeCompte, Boolean decouvert, float tauxRemuneration) {
        this.typeCompte = typeCompte;

        this.tauxRemuneration = tauxRemuneration;
    }

    
    
}
