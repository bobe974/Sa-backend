package com.etienne.sabackend.repository;

import com.etienne.sabackend.entites.Sentiment;
import com.etienne.sabackend.enums.TypeAvis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SentimentRepository extends JpaRepository<Sentiment, Integer> {
    List<Sentiment> findByType(TypeAvis typeAvis);
}
