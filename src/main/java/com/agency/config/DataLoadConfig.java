package com.agency.config;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agency.dao.ConseillerDao;
import com.agency.dao.RoleDao;
import com.agency.dao.UserDao;
import com.agency.enums.RoleName;
import com.agency.model.Conseiller;
import com.agency.model.Role;
import com.agency.payload.SignUpRequest;

@Component
public class DataLoadConfig {
	private final RoleDao roleRepository;
	
	@Autowired
	private UserDao userDao ; 
	
	@Autowired
	private ConseillerDao conseillerDao ;
	
//	@Autowired
//	private AuthController authController;

	public DataLoadConfig(RoleDao roleRepository) {
		this.roleRepository = roleRepository;
	}

	@PostConstruct
	public void loadData() {
		loadRoles();
	}

	public void loadRoles() {
		for (RoleName roleName : RoleName.values()) {
			boolean exist = roleRepository.existsByName(roleName);
			if (!exist) {
				roleRepository.save(new Role(roleName));
			}
		}
		boolean exist = userDao.existsByUsername("ADMIN");
		if (!exist) {
			
			SignUpRequest signUp = new SignUpRequest();
			signUp.setEmail("projet.spring.boot@gmail.com");
			signUp.setName("ADMIN");
			signUp.setRoleName("ROLE_ADMIN");
			signUp.setUsername("ADMIN");
			signUp.setPassword("admin");
			
//			this.authController.enregistreUser(signUp)	;
			
		}
		
		boolean exists = conseillerDao.existsByNom("Conseiller");
		if (!exists) {
			
			Conseiller con =new Conseiller() ;
			con.setEmail("conseiller@gmail.com");
			con.setNom("Conseiller");
			con.setPrenom("Junior");
			
			conseillerDao.save(con) ;
//			this.authController.enregistreUser(signUp)	;
			
		}

		
		
		
	}

}
