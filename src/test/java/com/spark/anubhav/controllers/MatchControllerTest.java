package com.spark.anubhav.controllers;

import com.spark.anubhav.models.Match;
import com.spark.anubhav.models.MatchDTO;
import com.spark.anubhav.models.UserMatchesDTO;
import com.spark.anubhav.services.MatchService;
import com.spark.anubhav.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchControllerTest {

    @Mock
    private MatchService matchService;

    @Test
    public void shouldReturnAllMatchesForTheUser() {
        UUID userId = UUID.randomUUID();
        List<Match> matchesForUser = List.of(TestUtils.buildMatch(userId));
        List<MatchDTO> expectedMatchesDTO = matchesForUser.stream().map(TestUtils::buildMatchDTO)
                .collect(Collectors.toList());

        when(matchService.findAllMatchesForUser(userId))
                .thenReturn(matchesForUser);

        UserMatchesDTO userMatches = new MatchController(matchService)
                .getAllMatchesForUser(userId);

        assertAll("Verify userMatches", () -> assertThat(userMatches.getUserId()).isEqualTo(userId),
                () -> assertThat(userMatches.getMatches())
                        .usingFieldByFieldElementComparator().containsAll(expectedMatchesDTO));

    }

    @Test
    public void shouldReturnEmptyListWhenNoMatchesForTheUser() {
        UUID userId = UUID.randomUUID();

        when(matchService.findAllMatchesForUser(userId))
                .thenReturn(List.of());

        UserMatchesDTO userMatches = new MatchController(matchService)
                .getAllMatchesForUser(userId);

        assertAll("Verify userMatches for no matches", () -> assertThat(userMatches.getUserId()).isEqualTo(userId),
                () -> assertThat(userMatches.getMatches())
                        .usingFieldByFieldElementComparator().isEmpty());

    }
}
