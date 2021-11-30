package com.agency.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agency.dao.ConseillerDao;
import com.agency.dao.UserDao;
import com.agency.model.Client;
import com.agency.model.Conseiller;
import com.agency.model.User;
import com.agency.payload.ApiResponse;
import com.agency.payload.SignUpRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/conseiller")
@Api(value = "conseiller ", description = "API d'accès aux ressources relative ")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ConseillerController {
	
	@Autowired 
	ConseillerDao conseillerDao; 
	@Autowired
	private AuthController authController;
	@Autowired 
	UserDao userDao;
	
	@GetMapping("/all")
	@ApiOperation(value = "liste des comseiller .")
	public ResponseEntity<?> getAllConseiller() {
		return ResponseEntity.ok(conseillerDao.findAll());
	}

	@GetMapping("/searchByName/{nom}")
	@ApiOperation(value = "conseiller par le nom.")
	public ResponseEntity<?> searchByName(@PathVariable(value = "nom") String nom) {
		try {
			List<Conseiller> conseillers = new ArrayList<>();
			conseillers = conseillerDao.findByNomContaining(nom);

			return ResponseEntity.ok(conseillers);

		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, "cient no found"), HttpStatus.BAD_REQUEST);

		}

	}

	@PostMapping("/create")
	@ApiOperation(value = " ajouter un conseiller .")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> addConseiller(@RequestBody @Valid Conseiller conseiller) {

		try {
			
			conseiller = conseillerDao.save(conseiller);
			
			SignUpRequest signUp = new SignUpRequest();
			signUp.setEmail(conseiller.getEmail());
			signUp.setName(conseiller.getNom());
			signUp.setRoleName("ROLE_CONSEILLER");
			signUp.setUsername(conseiller.getEmail());
			signUp.setPassword("123456");
			
			User user =   authController.enregistreUser(signUp); 
			user.setConseiller(conseiller) ;
			
			userDao.save(user) ; 
			
			return ResponseEntity.ok(null);
			
			}catch (ConstraintViolationException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
			e.printStackTrace();
			// System.out.println( e.getCause().getMessage());

			return new ResponseEntity<>( new ApiResponse(false,"Une erreur s'est produite"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
}
