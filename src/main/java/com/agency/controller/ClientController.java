package com.agency.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agency.dao.ClientDao;
import com.agency.dao.CompteDao;
import com.agency.dao.UserDao;
import com.agency.model.Client;
import com.agency.model.Compte;
import com.agency.model.User;
import com.agency.payload.ApiResponse;
import com.agency.payload.NewClient;
import com.agency.payload.SignUpRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/client")
@Api(value = "client ", description = "API d'accès aux ressources relative aux client")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClientController {

	@Autowired
	private ClientDao clientDao;
	@Autowired
	private CompteDao compteDao;
	
	@Autowired
	private AuthController authController;
	@Autowired
	private UserController userController;
	@Autowired
	UserDao userDao;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired 
	HttpServletRequest req ;

	@GetMapping("/all")
	@ApiOperation(value = "liste des .")
	public ResponseEntity<?> getAllClient() {
		return ResponseEntity.ok(clientDao.findAll());
	}

	@GetMapping("/getById/{id}")
	@ApiOperation(value = "client par id.")
	public ResponseEntity<?> getClientById(@PathVariable(value = "id") Long id) {
		try {
			return ResponseEntity.ok(clientDao.findByIdClient(id));

		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, "cient no found"), HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/searchByName/{nom}")
	@ApiOperation(value = "client par le nnom.")
	public ResponseEntity<?> searchByName(@PathVariable(value = "nom") String nom) {
		try {
			List<Client> clients = new ArrayList<>();
			clients = clientDao.findByNomContaining(nom);

			return ResponseEntity.ok(clients);

		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, "cient no found"), HttpStatus.BAD_REQUEST);

		}

	}

	@PostMapping("/create")
	@ApiOperation(value = " ajouter un client .")
	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
	public ResponseEntity<?> addClient(@RequestBody @Valid NewClient newClient) {

		try {
			

			Client client = newClient.getClient();
			User user = userController.whoami(req) ;
			
			if(user != null && user.getConseiller() != null ) {
				client.setGestionnaire(user.getConseiller()) ;
			}
			
			 

			Compte compte = client.getComptePrincipal() ;
			compte.setId(null);
			

			compte = compteDao.save(compte);

			String num = genererNum(String.valueOf(compte.getId()));
			
			compte.setNumCompte(num);
			
			client = clientDao.save(client);
			compte.setClient(client);
			
			compte = compteDao.save(compte);
			
			client.setComptePrincipal(compte) ;
			clientDao.save(client) ;

			return ResponseEntity.ok(compte);

		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(new ApiResponse(false, "Une erreur s'est produite"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/update")
	@ApiOperation(value = " update un client .")
	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
	public ResponseEntity<?> updateClient(@RequestBody @Valid Client client) {

		try {

			client = clientDao.saveAndFlush(client);

			return ResponseEntity.ok(client);

		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(new ApiResponse(false, "Une erreur s'est produite"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/searchByNumero/{numero}")
	@ApiOperation(value = "client par le numero de compte.")
	public ResponseEntity<?> searchByNumero(@PathVariable(value = "numero") String numero) {
		try {

			return ResponseEntity.ok(compteDao.findFirstByNumCompte(numero).getClient());

		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, "cient no found"), HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/inscription/{numero}")
	@ApiOperation(value = "inscription d'un client par son numero de compte.")
	public ResponseEntity<?> inscription(@PathVariable(value = "numero") String numero) {
		try {

			Client client = compteDao.findFirstByNumCompte(numero).getClient();
			if (client == null) {
				return new ResponseEntity<>(new ApiResponse(false, "client no found"), HttpStatus.BAD_REQUEST);
			}
			if (userDao.existsByEmail(client.getEmail())) {
				return new ResponseEntity<>(new ApiResponse(false, "Le client a déjà un compte"),
						HttpStatus.BAD_REQUEST);
			}

			SignUpRequest signUp = new SignUpRequest();
			signUp.setEmail(client.getEmail());
			signUp.setName(client.getNom());
			signUp.setRoleName("CLIENT");
			signUp.setUsername(client.getEmail());
			signUp.setPassword("123456");

			User user = this.authController.enregistreUser(signUp);
			user.setClient(client);

			userDao.save(user);

			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(user.getEmail());

			msg.setSubject("Identifiant d'accès à l'application Agency");
			msg.setText("Bonjour " + client.getNom() + " \n " + "" + "Votre Login : " + client.getEmail() + "  \n"
					+ " Votre mot de passe : " + "123456" + "\n"
					+ "Des votre premiere connexion, pensez a modifier votre mot de passe ");

			javaMailSender.send(msg);

			return ResponseEntity.ok(user);

		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, "cient no found"), HttpStatus.BAD_REQUEST);

		}

	}

	String genererNum(String chaine) {
		if (chaine.length() >= 8) {
			return chaine.substring(chaine.length() - 8);
		} else {

			do {
				chaine = "0" + chaine;

			} while (chaine.length() < 8);

			return chaine;
		}

	}

}
