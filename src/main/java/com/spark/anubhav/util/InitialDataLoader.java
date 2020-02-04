package com.spark.anubhav.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.anubhav.mappers.MatchMapper;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.models.DTOs.UserMatchesDTO;
import com.spark.anubhav.services.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
@Slf4j
public class InitialDataLoader {
    private final MatchService matchService;
    private final String jsonFileName;
    private final ObjectMapper objectMapper;
    private final boolean enabled;

    @Autowired
    public InitialDataLoader(MatchService matchService,
                             @Value("${spark.initialData.fileName:matches.json}") String jsonFileName,
                             @Value("${spark.initialDataLoad:false}") boolean enabled, ObjectMapper objectMapper) {
        this.matchService = matchService;
        this.jsonFileName = jsonFileName;
        this.enabled = enabled;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public List<Match> loadMatchesToDB() throws IOException {
        if (enabled) {
            log.info("started loading data from {}", jsonFileName);

            UserMatchesDTO matchDTOS = readFromJsonFile();
            final UUID userId = matchDTOS.getUserId();

            List<Match> matches = mapDTOToMatch(matchDTOS, userId);

            log.info("saving {} matches for user {}", matches.size(), userId);
            List<Match> savedMatches = matchService.addMatchesForUser(matches);
            log.info("saved {} matches for user {}", savedMatches.size(), userId);

            return savedMatches;
        }
        log.info("data loader is disabled, please enable it by adding 'spark.initialDataLoad' with true as value");
        return List.of();
    }

    private UserMatchesDTO readFromJsonFile() throws IOException {
        InputStream dataFile = getClass().getClassLoader().getResourceAsStream(jsonFileName);
        return mapJsonToUserMatches(dataFile);
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
