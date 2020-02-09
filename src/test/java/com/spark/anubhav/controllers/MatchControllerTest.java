package com.spark.anubhav.controllers;

import com.spark.anubhav.models.Match;
import com.spark.anubhav.models.DTOs.MatchDTO;
import com.spark.anubhav.models.DTOs.UserMatchesDTO;
import com.spark.anubhav.models.MatchQueryFilters;
import com.spark.anubhav.services.MatchService;
import com.spark.anubhav.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.spark.anubhav.utils.TestUtils.buildMatch;
import static com.spark.anubhav.utils.TestUtils.buildMatchDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchControllerTest {

    @Mock
    private MatchService matchService;
    private MatchController matchController;
    private static final UUID USER_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        matchController = new MatchController(matchService);
    }

    @Test
    public void shouldReturnAllMatchesForTheUser() {
        UUID userId = UUID.randomUUID();
        List<Match> matchesForUser = List.of(buildMatch(userId));
        List<MatchDTO> expectedMatchesDTO = matchesForUser.stream().map(TestUtils::buildMatchDTO)
                .collect(Collectors.toList());

        when(matchService.findAllMatchesForUser(userId))
                .thenReturn(matchesForUser);

        UserMatchesDTO actualUserMatches = matchController
                .getAllMatchesForUser(userId);

        assertAll("Verify userMatches", () -> assertThat(actualUserMatches.getUserId()).isEqualTo(userId),
                () -> assertThat(actualUserMatches.getMatches())
                        .usingFieldByFieldElementComparator().containsAll(expectedMatchesDTO));

    }

    @Test
    public void shouldReturnEmptyListWhenNoMatchesForTheUser() {

        when(matchService.findAllMatchesForUser(USER_ID))
                .thenReturn(List.of());

        UserMatchesDTO actualUserMatches = matchController
                .getAllMatchesForUser(USER_ID);

        assertAll("Verify userMatches for no matches", () -> assertThat(actualUserMatches.getUserId()).isEqualTo(USER_ID),
                () -> assertThat(actualUserMatches.getMatches())
                        .usingFieldByFieldElementComparator().isEmpty());

    }

    @Test
    public void shouldFilterOutTheMatchesWithOutPhotos() {
        Match aMatch = buildMatch(USER_ID);
        List<MatchDTO> expectedMatches = List.of(buildMatchDTO(aMatch));

        MatchQueryFilters matchQueryFilters = new MatchQueryFilters(true, null);
        when(matchService.findAllMatchesForUserBasedOnFilter(USER_ID, matchQueryFilters)).thenReturn(List.of(aMatch));

        UserMatchesDTO actualUserMatches = matchController.filterOutTheMatchesFotUser(USER_ID, matchQueryFilters);

        assertThat(actualUserMatches.getMatches())
                .usingFieldByFieldElementComparator()
                .containsAll(expectedMatches);
        assertThat(actualUserMatches.getUserId()).isEqualTo(USER_ID);
    }
}
