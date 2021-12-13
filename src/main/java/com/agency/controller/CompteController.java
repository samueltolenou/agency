package com.agency.controller;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/getById/{id}")
	@ApiOperation(value = "compte  par id.")
	public ResponseEntity<?> getCompteById(@PathVariable(value = "id") Long id) {
		try {
			return ResponseEntity.ok(compteDao.findById(id));

		} catch (Exception e) {
			e.printStackTrace() ;
			return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getByNumCompte/{numCompte}")
	@ApiOperation(value = "compte  par numCompte.")
	public ResponseEntity<?> getCompteBynumCompte(@PathVariable(value = "numCompte") String numCompte) {
		try {
			return ResponseEntity.ok(compteDao.findByNumCompteIs(numCompte));
		} catch (Exception e) {
			e.printStackTrace() ;
			return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

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
	@PostMapping("/cloturerCompte")
	@ApiOperation(value = " clôturer un compte à un client .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
	public ResponseEntity<?> cloturerCompte(@RequestBody @Valid Compte compte) {

		try {

			compte = compteDao.findFirstByNumCompte(compte.getNumCompte()) ;
			compte.setCloturer(true);
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
