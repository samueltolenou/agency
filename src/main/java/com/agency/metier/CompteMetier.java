package com.agency.metier;

import com.agency.model.Client;
import com.agency.model.Compte;

import java.util.List;

public interface CompteMetier {
    public Compte saveCompte(Compte cp);
    public Compte getCompte(Long num_compte);
    public List<Compte> listCompte();
}
