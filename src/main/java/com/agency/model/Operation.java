package com.agency.model;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "TYPE_OPERATION", discriminatorType = DiscriminatorType.STRING, length = 255)

@Data
@Entity
public class Operation implements Serializable {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
	
    private Long numOperation;
    private Date date;
    private Long montant;
    private Boolean signe;
    
    
    private TypeOperation typeOperation;
    private Date echeance;

   
    @ManyToOne
    private Compte compteDebiteur;
    
    @ManyToOne
    private Compte compteCrediteur ;

    public Operation() {
        super();
    }

    public Operation(Date date, long montant, Boolean signe, TypeOperation typeOperation, Date echeance) {
        this.date = date;
        this.montant = montant;
        this.signe = signe;
        this.typeOperation = typeOperation;
        this.echeance = echeance;
    }

}
