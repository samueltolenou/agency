package com.agency.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@Entity
public class Compte implements Serializable {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    
    private String numCompte;
    private Long solde;
    private Boolean active;
    private Boolean principal;
    private Boolean cloturer;
    private Boolean decouvert;
    private Date dateCreation ;
    
    @JsonBackReference
    @ManyToOne
    private Client client;

    @ManyToOne
    private TypeCompte typeCompte;

//    @OneToMany(mappedBy = "compte", fetch = FetchType.LAZY)
//    private List<Operation> operations;

    public Compte() {
        super();
    }

  

  
}
