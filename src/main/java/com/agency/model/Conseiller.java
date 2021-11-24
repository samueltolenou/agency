package com.agency.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
public class Conseiller implements Serializable {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    
	private String nom;
    private String prenom;
    
    @Email
    private String email;
    

    public Conseiller() {
        super();
    }

    public Conseiller(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

   
}
