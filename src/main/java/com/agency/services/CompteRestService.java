package com.agency.services;

import com.agency.dao.CompteDao;
import com.agency.metier.CompteMetier;
import com.agency.model.Compte;
import com.agency.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheableOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/compte")
public class CompteRestService {
    @Autowired
    private CompteMetier compteMetier;
    @Autowired
    private CompteDao compteDao;

    @PostMapping("/save")
    public Compte saveCompte(@RequestBody Compte cp) {
        return compteMetier.saveCompte(cp);
    }

    @GetMapping("/list")
    public List<Compte> listCompte(){
        return compteDao.findAll();
    }

    @GetMapping("/get/{num_compte}")
    public Compte getCompte(@PathVariable Long num_compte) {
        return compteMetier.getCompte(num_compte);
    }




}
