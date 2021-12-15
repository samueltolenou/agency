package com.agency.controller;

import com.agency.dao.ClientDao;
import com.agency.dao.CompteDao;
import com.agency.dao.OperationDao;
import com.agency.model.Compte;
import com.agency.model.Operation;
import com.agency.payload.ApiResponse;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/operation")
public class OperationController {
   
	@Autowired
    private OperationDao operationDao;
	@Autowired
    private CompteDao compteDao;
	@Autowired
    private ClientDao clientDao;

    @GetMapping("/list")
    public List<Operation> listOperation(){
        return operationDao.findAll();
    }
    
    @GetMapping("/listOperationByCompte/{numCompte}")
    public ResponseEntity<?> listOperationByCompte(@PathVariable(name = "numCompte", required = true)
    			String numCompte ) {
    	try {
			return ResponseEntity.ok(operationDao.findAllByCompteDebiteur_NumCompteOrCompteCrediteur_NumCompte(numCompte, numCompte)) ;

		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
  
    @PostMapping("/depot")
    @ApiOperation(value = "Pour faire un depot Si tout vas bien retourne operation")
    public ResponseEntity<?> depot (@RequestBody Operation operation){
    	try {
        	operation.setId(null) ;
        	operation.setDate(new Date());
			operation.setCompteDebiteur(null);

        	Compte compte = compteDao.findFirstByNumCompte(operation.getCompteCrediteur().getNumCompte()) ;
        	if(compte != null) {
        		compte.setSolde(compte.getSolde() + operation.getMontant()) ;
        		compte = compteDao.save(compte) ;
        		operation.setCompteCrediteur(compte) ;
        		
        		operation = operationDao.save(operation)  ;
        		operation.setNumOperation(operation.getId());
				operationDao.save(operation) ;
    			return ResponseEntity.ok(operation) ;
        	}
        	return new ResponseEntity<>(new ApiResponse(false, "compte not found"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
		
			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

		}
    }


    @PostMapping("/retrait")
    @ApiOperation(value = "Pour faire un retrait Si tout vas bien retourne operation")
    public ResponseEntity<?> retrait (@RequestBody Operation operation){
    	try {
        	operation.setId(null) ;
			operation.setDate(new Date());
			operation.setCompteCrediteur(null);

        	Compte compte = compteDao.findFirstByNumCompte(operation.getCompteDebiteur().getNumCompte()) ;
        	if(compte != null && compte.getSolde() > operation.getMontant()) {
        		
        		compte.setSolde(compte.getSolde() - operation.getMontant()) ;
        		compte = compteDao.save(compte) ;
        		operation.setCompteDebiteur(compte);
        		
        		operation = operationDao.save(operation)  ;
				operation.setNumOperation(operation.getId());
				operationDao.save(operation) ;
    			return ResponseEntity.ok(operation) ;
        	}
        	return new ResponseEntity<>(new ApiResponse(false, "compte not found"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
		
			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

		}
    }

	@PostMapping("/virementInterne")
	@ApiOperation(value = "Pour faire un virementInterne Si tout vas bien retourne operation")
	public ResponseEntity<?> virementInterne (@RequestBody Operation operation){
		try {
			operation.setId(null) ;
			operation.setDate(new Date());

			Compte compteCredit = compteDao.findFirstByNumCompte(operation.getCompteCrediteur().getNumCompte()) ;
			Compte compteDebit = compteDao.findFirstByNumCompte(operation.getCompteDebiteur().getNumCompte()) ;

			if(compteCredit != null && compteDebit != null) {
				// créditer le compte
				compteCredit.setSolde(compteCredit.getSolde() + operation.getMontant()) ;
				compteCredit = compteDao.save(compteCredit) ;
				operation.setCompteCrediteur(compteCredit) ;

				// débiter le compte
				compteDebit.setSolde(compteDebit.getSolde() - operation.getMontant()) ;
				compteDebit = compteDao.save(compteDebit) ;
				operation.setCompteCrediteur(compteDebit) ;

				operation = operationDao.save(operation)  ;
				operation.setNumOperation(operation.getId());
				operationDao.save(operation) ;
				return ResponseEntity.ok(operation) ;
			}
			return new ResponseEntity<>(new ApiResponse(false, "compte not found"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping("/virementExterne")
	@ApiOperation(value = "Pour faire un virementInterne Si tout vas bien retourne operation")
	public ResponseEntity<?> virementExterne (@RequestBody Operation operation){
		try {
			operation.setId(null) ;
			operation.setDate(new Date());

			Compte compteCredit = compteDao.findFirstByNumCompte(operation.getCompteCrediteur().getNumCompte()) ;
			Compte compteDebit = compteDao.findFirstByNumCompte(operation.getCompteDebiteur().getNumCompte()) ;

			if(compteCredit != null && compteDebit != null) {
				// créditer le compte
				compteCredit.setSolde(compteCredit.getSolde() + operation.getMontant()) ;
				compteCredit = compteDao.save(compteCredit) ;
				operation.setCompteCrediteur(compteCredit) ;

				// débiter le compte
				compteDebit.setSolde(compteDebit.getSolde() - operation.getMontant()) ;
				compteDebit = compteDao.save(compteDebit) ;
				operation.setCompteCrediteur(compteDebit) ;

				operation = operationDao.save(operation)  ;
				operation.setNumOperation(operation.getId());
				operationDao.save(operation) ;
				return ResponseEntity.ok(operation) ;
			}
			return new ResponseEntity<>(new ApiResponse(false, "compte not found"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}


	@GetMapping("/{id}")
    public Optional<Operation> getOneOperation(@PathVariable Long id){
        return operationDao.findById(id);
    }

    @PostMapping("/save")
    public Operation saveOperation (@RequestBody Operation operation){
        return operationDao.saveAndFlush(operation);
    }

    @DeleteMapping("/delete/{numOperation}")
    public boolean deleteOperation (@PathVariable Long numOperation){
        operationDao.deleteById(numOperation);
        return true;
    }


}
