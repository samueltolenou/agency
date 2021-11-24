package com.agency.metier;

import com.agency.model.Client;
import com.agency.model.Compte;

import java.util.List;

public interface ClientMetier {
    public Client saveClient(Client c);
    public List<Client> listClient();
    // public Client getClientByCopmpte()
    //public List<Compte> listComte();
}
