package com.etienne.sabackend.repository;

import com.etienne.sabackend.entites.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Integer> {

    Client findByEmail(String mail);
}
