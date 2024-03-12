package com.etienne.sabackend.controller;

import com.etienne.sabackend.entites.Client;
import com.etienne.sabackend.repository.ClientRepository;
import com.etienne.sabackend.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "client")
public class ClientController {



    //remplace @autowired
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void creer(@RequestBody Client client){
          this.clientService.creer(client);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Client> RecupererLesClient(){
        return clientService.RecupererToutLesClients();
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public Client RecupererParId(@PathVariable int id){
        return clientService.recupererClientParId(id);
    }


}