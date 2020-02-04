package com.spark.anubhav.services;

import com.spark.anubhav.models.Match;
import com.spark.anubhav.repositories.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MatchService {
    private MatchRepository repository;

    public MatchService(MatchRepository repository) {
        this.repository = repository;
    }

    public List<Match> findAllMatchesForUser(UUID userId) {
        return repository.findAllByUserId(userId);
    }

    public List<Match> addMatchesForUser(List<Match> matchesForUser) {
        List<Match> savedMatches = new ArrayList<>();
        matchesForUser.forEach(m -> m.setId(UUID.randomUUID()));
        repository.saveAll(matchesForUser).iterator().forEachRemaining(savedMatches::add);
        return savedMatches;
    }
}
