package com.spark.anubhav.services;

import com.querydsl.core.types.Predicate;
import com.spark.anubhav.filters.PredicateBuilder;
import com.spark.anubhav.models.Coordinates;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.models.MatchQueryFilters;
import com.spark.anubhav.repositories.MatchRepository;
import com.vividsolutions.jts.geom.GeometryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MatchService {
    private MatchRepository matchRepository;
    private GeometryFactory geometryFactory;

    @Autowired
    public MatchService(MatchRepository matchRepository, GeometryFactory geometryFactory) {
        this.matchRepository = matchRepository;
        this.geometryFactory = geometryFactory;
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

    public List<Match> findAllMatchesForUserBasedOnFilter(@Nonnull UUID userId, MatchQueryFilters matchQueryFilters, Coordinates userCoordinates) {
        log.info("retrieving matches for user {} with filters {}", userId, matchQueryFilters.toString());

        Predicate predicate = createPredicateForTheFilters(userId, matchQueryFilters, userCoordinates);
        List<Match> matchesRetrieved = (List<Match>) matchRepository.findAll(predicate);

        log.info("successfully retrieved {} mactches for user {}", matchesRetrieved.size(), userId);

        return matchesRetrieved;
    }

    private Predicate createPredicateForTheFilters(UUID userId, MatchQueryFilters queryFilters, Coordinates userCoordinates) {
        return PredicateBuilder.builder()
                .forUser(userId)
                .hasPhoto(queryFilters.getHasPhoto())
                .isFavorite(queryFilters.getIsFavorite())
                .withCompatibility(queryFilters.getCompatibilityRange())
                .withAgeBetween(queryFilters.getAgeRange())
                .withHeightBetween(queryFilters.getHeightRange())
                .isAlreadyAContact(queryFilters.getInContact())
                .livingWithIn(queryFilters.getDistanceRange(),
                        userCoordinates, geometryFactory)
                .build();
    }

    private void setIdForAllMatches(List<Match> matchesForUser) {
        matchesForUser.forEach(m -> m.setId(UUID.randomUUID()));
    }
}
