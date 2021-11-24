package com.agency.metier;

import com.agency.dao.ClientDao;
import com.agency.dao.CompteDao;
import com.agency.model.Client;
import com.agency.model.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ClientMetierImpl implements ClientMetier{

    @Autowired
    private ClientDao clientDao;
    @Autowired
    private CompteDao compteDao;

    @Override
    public Client saveClient(Client c) {
        return clientDao.save(c);
    }

    @Override
    public List<Client> listClient() {
        return clientDao.findAll();
    }


}
