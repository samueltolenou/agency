package com.agency.controller;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agency.dao.ClientDao;
import com.agency.dao.CompteDao;
import com.agency.model.Compte;
import com.agency.payload.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/compte")
@Api(value = "compte ", description = "API d'accès aux ressources relative aux compte")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
public class CompteController {

	@Autowired
	private ClientDao clientDao;
	@Autowired
	private CompteDao compteDao; 
	
	@PostMapping("/create")
	@ApiOperation(value = " ajouter un compte à un client .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
	public ResponseEntity<?> addCompte(@RequestBody @Valid Compte compte) {

		try {
			
			compte = compteDao.save(compte) ;
			
			String num = genererNum(String.valueOf(compte.getId()));
			compte.setNumCompte(num) ;
			
			compte = compteDao.save(compte);
			
			return ResponseEntity.ok(compte);
			
			}catch (ConstraintViolationException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
			e.printStackTrace();
			
			return new ResponseEntity<>( new ApiResponse(false,"Une erreur s'est produite"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	
	
	String genererNum(String chaine) {
		if(chaine.length()>=8) {
			return chaine.substring(chaine.length()-8);
		}else {
			
			do {
				chaine = "0"+chaine;
				
			}while(chaine.length() < 8);
			 
			return chaine;
		}
		
	}
	
}
