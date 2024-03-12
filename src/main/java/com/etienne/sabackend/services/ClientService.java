package com.etienne.sabackend.services;

import com.etienne.sabackend.entites.Client;
import com.etienne.sabackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {

    // //remplace @autowired utlise l'inversion de controle (ioc) pour instancier le Repo
    private ClientRepository clientRepository;
    public  ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }
    public void creer(Client client){
        //verifie si le client existe deja (par mail)
        if(this.clientRepository.findByEmail(client.getEmail()) == null){
            this.clientRepository.save(client);
        }

    }

    public List<Client> RecupererToutLesClients(){
        return this.clientRepository.findAll();
    }

    public Client recupererClientParId(int id){
        Optional<Client> optionalClient =  this.clientRepository.findById(id);
        if(optionalClient.isPresent()){
            return optionalClient.get();
        }
        return null;
    }

    public Client LireOuCreer(Client client){
       Client clientDansLaBdd = clientRepository.findByEmail(client.getEmail());
       if(Objects.isNull(clientDansLaBdd)){
           clientDansLaBdd = clientRepository.save(client);
       }
       return clientDansLaBdd;
    }
}
