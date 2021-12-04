package com.agency.controller;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.agency.dao.RoleDao;
import com.agency.dao.UserDao;
import com.agency.enums.RoleName;
import com.agency.enums.StatusMessage;
import com.agency.exceptions.AppException;
import com.agency.model.Conseiller;
import com.agency.model.Role;
import com.agency.model.User;
import com.agency.payload.ApiResponse;
import com.agency.payload.JwtAuthenticationResponse;
import com.agency.payload.LoginRequest;
import com.agency.payload.SignUpRequest;
import com.agency.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDao userRepository;

	@Autowired
	RoleDao roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
    private JavaMailSender javaMailSender;

	/**
	 *
	 * @param loginRequest
	 * @return
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getUsernameOrEmail();
		Optional<User> user = userRepository.findByUsernameOrEmail(username, username);
		if (user.isPresent()) {
			username = user.get().getUsername();
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	/*
	 *
	 * @param signUpRequest
	 * @return
	 */
	@PostMapping("/signup")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		try {

			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
				return new ResponseEntity<>(
						new ApiResponse(false, StatusMessage.USERNAME_EXIST.getReasonPhrase(),
								StatusMessage.USERNAME_EXIST.getValue(), "Username is already taken!"),
						HttpStatus.BAD_REQUEST);
			}

			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				return new ResponseEntity<>( new ApiResponse(false, StatusMessage.EMAIL_EXIST.getReasonPhrase(),
								StatusMessage.EMAIL_EXIST.getValue(), "Email Address already in use!"),
						HttpStatus.BAD_REQUEST);
			}

			// Creating user's account
			User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
					signUpRequest.getPassword());

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			Role userRole = roleRepository.findByName(RoleName.valueOf(signUpRequest.getRoleName()))
					.orElseThrow(() -> new AppException("User Role not set."));

			user.setRoles(Collections.singleton(userRole));

			User result = userRepository.save(user);

			URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}")
					.buildAndExpand(result.getUsername()).toUri();

			return ResponseEntity.created(location)
					.body(new ApiResponse(true, StatusMessage.USER_REGISTERED.getReasonPhrase(),
							StatusMessage.USER_REGISTERED.getValue(), "User registered successfully"));

		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.getMessage(),
					StatusMessage.INTERNAL_SERVER_ERROR.getValue(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	public User enregistreUser(@Valid SignUpRequest signUpRequest) {
		try {

			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
				return null;
			}

			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				return null;
			}

			// Creating user's account
			User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
					signUpRequest.getPassword());

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			Role userRole = roleRepository.findByName(RoleName.valueOf(signUpRequest.getRoleName()))
					.orElseThrow(() -> new AppException("User Role not set."));

			user.setRoles(Collections.singleton(userRole));

			user = userRepository.save(user);

			return user ;

		} catch (Exception e) {
			return null ;
		}

	}
	
	public User enregistreUserConseiller(@Valid SignUpRequest signUpRequest,Conseiller cons) {
		try {

			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
				return null;
			}

			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				return null;
			}

			// Creating user's account
			User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
					signUpRequest.getPassword());

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			Role userRole = roleRepository.findByName(RoleName.valueOf(signUpRequest.getRoleName()))
					.orElseThrow(() -> new AppException("User Role not set."));

			user.setRoles(Collections.singleton(userRole));

			user.setConseiller(cons);
			user = userRepository.save(user);

			return user ;

		} catch (Exception e) {
			return null ;
		}

	}
	
	@PostMapping("/sendMail")
	public ResponseEntity<?> sendMail () {
	try{
		 	SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo("samuel.tolenou.pro@gmail.com");

	        msg.setSubject("Testing from Spring Boot");
	        msg.setText("Hello World \n Spring Boot Email");

	        javaMailSender.send(msg);
		
	        return ResponseEntity.ok("opération réussie");
	}catch (Exception e) {
		e.printStackTrace();
		
		return null ;
	}
		
	}
	
	
	
	
	
	
}
