package com.agency.controller;

import com.agency.dao.BanqueDao;
import com.agency.model.Banque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600 , allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/banque")
public class BanqueController {

	
    @Autowired
    private BanqueDao banqueDao;

    @GetMapping("/list")
    public List<Banque> listBanque(){
        return banqueDao.findAll();
    }
    @GetMapping("/{idBanque}")
    public Optional<Banque> getOneEc(@PathVariable Long idBanque){
        return banqueDao.findById(idBanque);
    }

    @PostMapping("/save")
    public Banque save(@RequestBody Banque banque){
        return banqueDao.saveAndFlush(banque);
    }

    @DeleteMapping("/delete/{idBanque}")
    public boolean deleteBanque (@PathVariable Long idBanque){
        banqueDao.deleteById(idBanque);
        return true;
    }

}
