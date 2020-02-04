package com.spark.anubhav.services;

import com.spark.anubhav.models.Match;
import com.spark.anubhav.repositories.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static com.spark.anubhav.utils.TestUtils.buildMatch;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {
    @Mock
    private MatchRepository repository;
    private MatchService matchService;

    @BeforeEach
    void setUp() {
        matchService = new MatchService(repository);
    }

    @Test
    public void shouldGetAllTheMatches() {
        UUID userId = UUID.randomUUID();
        Match aMatch = buildMatch(userId);

        when(repository.findAllByUserId(userId)).thenReturn(Collections.singletonList(aMatch));

        List<Match> matches = matchService.findAllMatchesForUser(userId);

        assertThat(matches).containsExactly(aMatch);
    }

    @Test
    public void shouldBeAbleToAddMatchesForTheUser() {
        UUID userId = UUID.randomUUID();
        Match aMatch = buildMatch(userId);
        List<Match> matchesForUser = Collections.singletonList(aMatch);

        when(repository.saveAll(matchesForUser)).thenReturn(matchesForUser);

        List<Match> matches = matchService.addMatchesForUser(matchesForUser);

        verify(repository).saveAll(matchesForUser);
        assertAll("verify if correct matches are added and id is generated",
                () -> assertThat(matches)
                        .usingElementComparatorIgnoringFields("id")
                        .containsExactly(aMatch),
                () -> assertThat(matches.get(0).getId())
                        .isNotNull()
        );

    }

}
