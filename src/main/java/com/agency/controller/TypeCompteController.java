package com.agency.controller;

import com.agency.dao.TypeCompteDao;
import com.agency.model.TypeCompte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/type-compte")
public class TypeCompteController {
    @Autowired
    private TypeCompteDao typeCompteDao;

    @GetMapping("/list")
    public List<TypeCompte> listTypeCompte(){
        return typeCompteDao.findAll();
    }


    @GetMapping("/{idTypeCompte}")
    public Optional<TypeCompte> getOneTypeCompte(@PathVariable Long idTypeCompte){
        return typeCompteDao.findById(idTypeCompte);
    }

    @PostMapping("/save")
    public TypeCompte saveTypeCOmpte(@RequestBody TypeCompte typeCompte){
        return typeCompteDao.saveAndFlush(typeCompte);
    }

    @DeleteMapping("/delete/{idTypeCompte}")
    public boolean deleteTypeCompte (@PathVariable Long idTypeCompte){
        typeCompteDao.deleteById(idTypeCompte);
        return true;
    }
}
