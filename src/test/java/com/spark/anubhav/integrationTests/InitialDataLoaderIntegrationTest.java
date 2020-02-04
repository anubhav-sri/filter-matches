package com.spark.anubhav.integrationTests;

import com.spark.anubhav.MatchFilterApplication;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.repositories.MatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MatchFilterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InitialDataLoaderIntegrationTest {

    @Autowired
    private MatchRepository matchRepository;

    @Test
    public void shouldLoadInitialDataUsingJsonFile() {
        List<Match> matchesForUser =
                matchRepository.findAllByUserId(UUID.fromString("31eed42a-fdd1-4751-bcf3-00a4c8e40d7e"));
        assertThat(matchesForUser.size()).isEqualTo(2);
    }
}
