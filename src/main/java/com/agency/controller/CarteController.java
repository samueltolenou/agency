package com.agency.controller;

import com.agency.dao.CarteBancaireDao;
import com.agency.model.CarteBancaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/carte")
public class CarteController {

    @Autowired
    private CarteBancaireDao carteBancaireDao;

    @GetMapping("/list")
    public List<CarteBancaire> listCarteBancaire(){
        return carteBancaireDao.findAll();
    }


    @GetMapping("/{numCarte}")
    public Optional<CarteBancaire> getOneEc(@PathVariable Long numCarte){
        return carteBancaireDao.findById(numCarte);
    }

    @PostMapping("/save")
    public CarteBancaire save(@RequestBody CarteBancaire carteBancaire){
        return carteBancaireDao.saveAndFlush(carteBancaire);
    }

    @DeleteMapping("/delete/{numCarte}")
    public boolean deleteAnneAcad (@PathVariable Long numCarte){
        carteBancaireDao.deleteById(numCarte);
        return true;
    }
}
