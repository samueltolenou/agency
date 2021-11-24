package com.agency.model;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class Banque implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBanque;
    private String nom;
    private String adresse;

    public Banque() {
        super();
    }

    public Banque(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
    }

}
