package com.agency.payload;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.agency.model.Client;
import com.agency.model.Compte;
import com.agency.model.Conseiller;
import com.agency.model.TypeCompte;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data

@AllArgsConstructor
public class NewClient {

	@NotNull
	Client client ;
	
	@NotNull
	Compte compte;
	
	
}
