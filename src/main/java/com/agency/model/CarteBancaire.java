package com.agency.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.agency.enums.TypeCarte;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@Entity
public class CarteBancaire implements Serializable {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id ;
	
    private Long numCarte;
    
    @Enumerated(EnumType.STRING)
    private TypeCarte typeCarte;
    private LocalDate echeance;
    private String codeCrypto;
    

    @ManyToOne
    private Client client;

    public CarteBancaire() {
        super();
    }

 

   
    
}
