package com.agency.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.aspectj.apache.bcel.classfile.Code;

import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class TypeOperation implements Serializable {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
	private String code ;
    private String libelleOperation;

    public TypeOperation() {
        super();
    }

    public TypeOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

   
}
