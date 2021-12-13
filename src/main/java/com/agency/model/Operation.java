package com.agency.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Operation implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
	
    private Long numOperation;
    private Date date = new Date();
    private Long montant;
    private Boolean signe;
    

    @ManyToOne
    private TypeOperation typeOperation;
    private LocalDate echeance;

   
    @ManyToOne
    private Compte compteDebiteur;
    
    @ManyToOne
    private Compte compteCrediteur ;



}
