package com.agency.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agency.dao.UserDao;
import com.agency.model.User;
import com.agency.payload.ApiResponse;
import com.agency.payload.ResetPassword;
import com.agency.payload.UserIdentityAvailability;
import com.agency.security.CurrentUser;
import com.agency.security.UserPrincipal;

import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/user")
@Api(value = "Users", description = "Users controllers details.", tags = { "Users" })
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserDao userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired 
    HttpServletRequest req ;
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    
//
//    @Autowired
//    MailService mailService;


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_CLIENT') or hasRole('ROLE_CONSEILLER')")
    @ApiOperation(value = "${UserController.me}")
    public User whoami(HttpServletRequest req) {
       return userRepository.findById(getId(resolveToken(req))).get();
    }
    
    
    
    public Long getId(String token) {
    	String idStr = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    	return Long.valueOf(idStr) ;
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
    
    
    @GetMapping("/oldme")
    @PreAuthorize(" hasRole('USER') or hasRole('CLIENT') or hasRole('CONSEILLER') or hasRole('ADMIN') ")
    @ApiOperation(value = "This ressource is used to get connected user details. ADMIN or USER account is necessary to master this operation..")
    public User getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        //UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userRepository.findById(currentUser.getId()).get();
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_CLIENT') or hasRole('ROLE_CONSEILLER') or hasRole('ROLE_ADMIN') ")
    @ApiOperation(value = "This ressource is used to retrieve user details based on his id. ADMIN or USER account is necessary to master this operation.")
    public User getCurrentUser(@PathVariable Long id) {
        if(userRepository.findById(id).isPresent()){
            return userRepository.findById(id).get();
        }else{
            return null;
        }
    }

    @GetMapping("/checkUsernameAvailability")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_CLIENT') or hasRole('ROLE_CONSEILLER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "This ressource is used to check username avalability. ADMIN account is necessary to master this operation.")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/checkEmailAvailability")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_CLIENT') or hasRole('ROLE_CONSEILLER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "This ressource is used to check email avalability. ADMIN account is necessary to master this operation.")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }


//    @PostMapping("/create-user")
//    @ApiOperation(value = "Permet de créer un utilisateur.")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<?> registerUser(@RequestBody User user) {
//        if(userRepository.existsByUsername(user.getUsername())) {
//            return new ResponseEntity(new ApiResponse(false, "Cet nom d'utilisateur est déjà pris par un autre utilisateur!"),
//                    HttpStatus.BAD_REQUEST);
//        }
//
//        if(userRepository.existsByEmail(user.getEmail())) {
//            return new ResponseEntity(new ApiResponse(false, "Cette addresse email est déjà utilisée par un autre utiisateur!"),
//                    HttpStatus.BAD_REQUEST);
//        }
//
//        //System.out.println(user.getPassword());
//        String pwdEncode = passwordEncoder.encode("12345");
//        user.setPassword(pwdEncode);
//        User result = userRepository.save(user);
//
//        String subject = "Notification de creation de compte.";
//        String helloName = "Monsieur / Madame "+user.getName();
//        StringBuilder message = new StringBuilder();
//        message.append(".<br/>");
//        message.append("Des votre premiere connexion, pensez a modifier votre mot de passe par defaut qui est genere par le systeme.<br/><br/>");
//        message.append("Votre login : "+ user.getUsername()+"<br/>");
//        message.append("Votre mot de passe  : <b>"+ 12345 +"</b><br/>");
//        //mailService.sendMail(user.getEmail(), subject, message.toString(), helloName);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/users/{username}")
//                .buildAndExpand(result.getUsername()).toUri();
//
//        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
//    }

//    @GetMapping("/list-user")
//    @ApiOperation(value = "Permet de récupérer la liste des utilisateurs.")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<User>> listOfUsrers() {
//
//        List<User> users = userRepository.findAll();
//        return ResponseEntity.ok(users);
//    }


    @PostMapping("/update-user")
    @ApiOperation(value = "This ressource is used to update user account. ADMIN account is necessary to master this operation.")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user) {

        User result = null;

        System.out.println("Password =" + user.getPassword());
        if(userRepository.existsById(user.getId())){

            User r = userRepository.getOne(user.getId());
            if(user.getPassword()!= ""){
                //String pwd = passwordEncoder.encode(user.getPassword());
                user.setPassword(r.getPassword());
                result = userRepository.save(user);
            }else{
                return new ResponseEntity<>(new ApiResponse(false, "Le mot de passe ne peut pas être vide."),
                        HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.ok(result);
    }

//    @PostMapping("/delete-user")
//    @ApiOperation(value = "Permet de supprimer un utilisateur.")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<?> deleteUser(@Valid @RequestBody User user) {
//
//        User result = null;
//
//        if(userRepository.existsById(user.getId())){
//            result = userRepository.save(user);
//        }
//
//        return new ResponseEntity(new ApiResponse(true, "Suppression effectué avec succès."),
//                HttpStatus.OK);
//    }

    @ApiOperation(value = "This ressource is used to reset user password. ADMIN or USER account is necessary to master this operation.")
    @PostMapping("/reset-password")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_CLIENT') or hasRole('ROLE_CONSEILLER') or hasRole('ROLE_ADMIN') ")
    public ResponseEntity<?> resetPassword(@CurrentUser UserPrincipal userPrincipal, @RequestBody ResetPassword resetPassword) {

        Optional<User> updateUser = userRepository.findById(userPrincipal.getId());

        updateUser.get().setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));

        User userSaved = userRepository.save(updateUser.get());

        return new ResponseEntity<>(new ApiResponse(true, "Modification du mot de passe effectué avec succès."),
                HttpStatus.OK);
    }

}
