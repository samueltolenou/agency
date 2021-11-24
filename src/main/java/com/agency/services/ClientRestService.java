package com.agency.services;

import com.agency.dao.CompteDao;
import com.agency.metier.ClientMetier;
import com.agency.model.Client;
import com.agency.model.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ClientRestService {

    @Autowired
    private ClientMetier clientMetier;
    @Autowired
    private CompteDao compteDao;
    @PostMapping("/save")
    public Client saveClient(@RequestBody Client c) {
        return clientMetier.saveClient(c);
    }

    @GetMapping("/list")
    public List<Client> listClient() {
        return clientMetier.listClient();
    }



}
