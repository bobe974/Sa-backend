package com.etienne.sabackend.controller;

import com.etienne.sabackend.entites.Sentiment;
import com.etienne.sabackend.enums.TypeAvis;
import com.etienne.sabackend.services.SentimentService;
import exceptions.AvisIntrouvableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "avis")
public class SentimentController {

    private SentimentService sentimentService;

    public SentimentController(SentimentService sentimentService) {
        this.sentimentService = sentimentService;
    }

    //code 200
    @CrossOrigin
    @GetMapping
    public List<Sentiment> recupererSentiments(@RequestParam(required = false) TypeAvis typeAvis) {
        return sentimentService.rechercher(typeAvis);
    }

    @GetMapping(path = "{id}")
    public Sentiment recupererSentiment(@PathVariable int id) {
        return sentimentService.recupererAvisParId(id);
    }

    //code 201 created
    @PostMapping()
    public ResponseEntity<Sentiment> creer(@RequestBody Sentiment sentiment) {
        if (Objects.isNull(sentiment)) {
            return ResponseEntity.noContent().build();
        }
        sentimentService.creer(sentiment);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sentiment.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //mise a jour code 204
    //@ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(path = "{id}")
    public ResponseEntity<Sentiment> modifieAvis(@RequestBody Sentiment sentiment, @PathVariable int id) {
        if (Objects.isNull(sentiment)) {
            return ResponseEntity.noContent().build();
        }
        try {
            sentimentService.modifie(sentiment, id);
            return ResponseEntity.ok(sentiment);
        } catch (AvisIntrouvableException a) {
            return ResponseEntity.notFound().build();
        }
    }

    //suppression code 204
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Integer id) {
        Sentiment sentiment = sentimentService.recupererAvisParId(id);
        if (Objects.isNull(sentiment)) {
            throw new AvisIntrouvableException("Avis introuvable !");
        }
        sentimentService.supprime(sentiment);
        return ResponseEntity.noContent().build();
    }


}
