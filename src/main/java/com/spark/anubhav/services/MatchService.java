package com.spark.anubhav.services;

import com.querydsl.core.types.Predicate;
import com.spark.anubhav.filters.PredicateBuilder;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.repositories.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MatchService {
    private MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> findAllMatchesForUser(UUID userId) {
        log.info("retrieving matches for user {}", userId);

        List<Match> retrievedMatchesForUser = matchRepository.findAllByUserId(userId);

        log.info("retrieved {} matches for user {}", retrievedMatchesForUser.size(), userId);

        return retrievedMatchesForUser;
    }

    public List<Match> addMatchesForUser(List<Match> matchesForUser) {
        log.info("Creating Ids for {} matches for user", matchesForUser.size());
        setIdForAllMatches(matchesForUser);

        log.info("Started Saving {} matches for user", matchesForUser.size());

        List<Match> savedMatches = new ArrayList<>();
        matchRepository.saveAll(matchesForUser).iterator().forEachRemaining(savedMatches::add);

        log.info("Completed Saving {} matches for user", matchesForUser.size());
        return savedMatches;
    }

    public List<Match> findAllMatchesForUserBasedOnFilter(@Nonnull UUID userId, Boolean hasPhoto) {

        Predicate predicate = createPredicateForTheFilters(userId, hasPhoto);

        return (List<Match>) matchRepository.findAll(predicate);
    }

    private Predicate createPredicateForTheFilters(UUID userId, Boolean hasPhoto) {
        return PredicateBuilder.builder()
                .forUser(userId)
                .hasPhoto(hasPhoto)
                .build();
    }

    private void setIdForAllMatches(List<Match> matchesForUser) {
        matchesForUser.forEach(m -> m.setId(UUID.randomUUID()));
    }
}
