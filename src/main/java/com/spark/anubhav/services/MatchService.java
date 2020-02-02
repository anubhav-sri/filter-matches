package com.spark.anubhav.services;

import com.spark.anubhav.models.Match;
import com.spark.anubhav.repositories.MatchRepository;

import java.util.List;
import java.util.UUID;

public class MatchService {
    private MatchRepository repository;

    public MatchService(MatchRepository repository) {
        this.repository = repository;
    }

    public List<Match> findAllMatchesForUser(UUID userId) {
        return repository.findAllByUserId(userId);
    }
}
