package com.etienne.sabackend.services;

import com.etienne.sabackend.entites.Client;
import com.etienne.sabackend.entites.Sentiment;
import com.etienne.sabackend.enums.TypeAvis;
import com.etienne.sabackend.repository.SentimentRepository;
import exceptions.AvisIntrouvableException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class SentimentService {
    private SentimentRepository sentimentRepository;
    private ClientService clientService;

    //injection par inversion de controle ioc
    public SentimentService(SentimentRepository sentimentRepository, ClientService clientService) {
        this.sentimentRepository = sentimentRepository;
        this.clientService = clientService;
    }

    public List<Sentiment> rechercher(TypeAvis typeAvis) {
        if (typeAvis == null){
            return this.sentimentRepository.findAll();
        }else {
            //trie par type
            return sentimentRepository.findByType(typeAvis);
        }

    }

    public Sentiment recupererAvisParId(int id) {
        Optional<Sentiment> optionalAvis = this.sentimentRepository.findById(id);
        if (optionalAvis.isPresent()) {
            return optionalAvis.get();
        }
        return null;
    }

    public void creer(Sentiment sentiment) {
        sentiment.setClient(clientService.LireOuCreer(sentiment.getClient()));
        this.sentimentRepository.save(sentiment);
    }

    public void supprime(Sentiment sentiment) {
        sentimentRepository.delete(sentiment);
    }

    public void modifie(Sentiment nouveauSentiment, Integer id) {
        //recupere l'avis présent dans la base correspond a l'id
        Optional<Sentiment> sentimentbdd = sentimentRepository.findById(id);
        if (sentimentbdd.isPresent()) {
            Sentiment sentiment = sentimentbdd.get();
            sentiment.setAvis((nouveauSentiment.getAvis() != null) ? nouveauSentiment.getAvis() : sentiment.getAvis());
            sentiment.setClient((nouveauSentiment.getClient() != null) ? nouveauSentiment.getClient() : sentiment.getClient());
            sentiment.setType((nouveauSentiment.getType() != null) ? nouveauSentiment.getType() : sentiment.getType());
            sentimentRepository.save(sentiment);
        } else {
            throw new AvisIntrouvableException("Avis introuvable");
        }

    }
}
