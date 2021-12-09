package com.agency.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.agency.enums.TypeClient;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity

public class Client implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
	
    private String nom;
    private String prenom;
    private String email;
    //private String typeClient1;
    private String numContrat;
    private String numAgence;
    private String Tel;
    
    @Enumerated(EnumType.STRING)
    private TypeClient typeClient;
    
    private Date dateNaissance;
    private String sexe;
    private String ville;
    private String proffession;
    
    @ManyToOne
    private Compte comptePrincipal ;

    @JsonManagedReference
    @OneToMany(mappedBy = "client" /*, fetch = FetchType.EAGER*/)
    private List<Compte> comptes;
    
    @ManyToOne
    private Conseiller gestionnaire ;

    public Client() {
        super();
    }

  
   
}
