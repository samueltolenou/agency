package com.agency.payload;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.agency.model.Client;
import com.agency.model.Compte;
import com.agency.model.Conseiller;
import com.agency.model.TypeCompte;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class NewClient implements Serializable {

	@NotNull
	Client client ;
	
//	@NotNull
//	Compte compte;
//	
	
}
