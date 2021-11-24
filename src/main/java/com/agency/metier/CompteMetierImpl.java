package com.agency.metier;

import com.agency.dao.CompteDao;
import com.agency.metier.CompteMetier;
import com.agency.model.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompteMetierImpl implements CompteMetier {

    @Autowired
    private CompteDao compteDao;
    @Override
    public Compte saveCompte(Compte cp) {
        return compteDao.save(cp);
    }

    @Override
    public Compte getCompte(Long num_compte) {
        return compteDao.getById(num_compte);
    }

    @Override
    public List<Compte> listCompte() {
        return compteDao.findAll();
    }
}
