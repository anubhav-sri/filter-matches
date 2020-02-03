package com.spark.anubhav.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.anubhav.mappers.MatchMapper;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.models.UserMatchesDTO;
import com.spark.anubhav.repositories.MatchRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class InitialDataLoader {
    private final MatchRepository matchRepository;
    private final String jsonFileName;
    private final ObjectMapper objectMapper;
    private boolean enabled;

    public InitialDataLoader(MatchRepository matchRepository, String jsonFileName, boolean enabled, ObjectMapper objectMapper) {
        this.matchRepository = matchRepository;
        this.jsonFileName = jsonFileName;
        this.enabled = enabled;
        this.objectMapper = objectMapper;
    }

    public List<Match> loadMatchesToDB() throws IOException {
        if (enabled) {
            InputStream dataFile = getClass().getClassLoader().getResourceAsStream(jsonFileName);
            UserMatchesDTO matchDTOS = mapJsonToUserMatches(dataFile);
            final UUID userId = matchDTOS.getUserId();
            List<Match> matches = mapDTOToMatch(matchDTOS, userId);
            matchRepository.saveAll(matches);
            return matches;
        }
        return List.of();
    }

    private List<Match> mapDTOToMatch(UserMatchesDTO matchDTOS, UUID userId) {
        return matchDTOS.getMatches()
                .stream()
                .map(m -> MatchMapper.mapDTOtoMatch(userId, m))
                .collect(Collectors.toList());
    }

    private UserMatchesDTO mapJsonToUserMatches(InputStream dataFile) throws IOException {
        return objectMapper.readValue(requireNonNull(dataFile),
                new TypeReference<>() {
                });
    }
}
