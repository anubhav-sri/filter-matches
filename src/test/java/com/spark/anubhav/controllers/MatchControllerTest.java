package com.spark.anubhav.controllers;

import com.spark.anubhav.models.AgeRange;
import com.spark.anubhav.models.Coordinates;
import com.spark.anubhav.models.DTOs.MatchDTO;
import com.spark.anubhav.models.DTOs.UserMatchesDTO;
import com.spark.anubhav.models.Match;
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

import static com.spark.anubhav.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchControllerTest {

    @Mock
    private MatchService matchService;
    private MatchController matchController;
    private static final UUID USER_ID = UUID.randomUUID();
    private MatchQueryFilters emptyQueryFilter;

    @BeforeEach
    void setUp() {
        matchController = new MatchController(matchService);
        emptyQueryFilter = new MatchQueryFilters(null, null, null, null, null, null, null);
    }

    @Test
    public void shouldReturnAllMatchesForTheUser() {
        List<Match> matchesForUser = List.of(buildMatch(USER_ID));
        List<MatchDTO> expectedMatchesDTO = matchesForUser.stream().map(TestUtils::buildMatchDTO)
                .collect(Collectors.toList());

        when(matchService.findAllMatchesForUserBasedOnFilter(USER_ID, emptyQueryFilter, new Coordinates(null, null)))
                .thenReturn(matchesForUser);

        UserMatchesDTO actualUserMatches = matchController
                .findAllMatchesUsingFilters(USER_ID, emptyQueryFilter, null, null);

        assertAll("Verify userMatches", () -> assertThat(actualUserMatches.getUserId()).isEqualTo(USER_ID),
                () -> assertThat(actualUserMatches.getMatches())
                        .usingFieldByFieldElementComparator().containsAll(expectedMatchesDTO));

    }

    @Test
    public void shouldReturnEmptyListWhenNoMatchesForTheUser() {

        when(matchService.findAllMatchesForUserBasedOnFilter(USER_ID, emptyQueryFilter, new Coordinates(null, null)))
                .thenReturn(List.of());

        UserMatchesDTO actualUserMatches = matchController
                .findAllMatchesUsingFilters(USER_ID, emptyQueryFilter, null, null);

        assertAll("Verify userMatches for no matches", () -> assertThat(actualUserMatches.getUserId()).isEqualTo(USER_ID),
                () -> assertThat(actualUserMatches.getMatches())
                        .usingFieldByFieldElementComparator().isEmpty());

    }

    @Test
    public void shouldFilterOutTheMatchesWithOutPhotos() {
        Match aMatch = buildMatch(USER_ID);
        List<MatchDTO> expectedMatches = List.of(buildMatchDTO(aMatch));

        MatchQueryFilters matchQueryFilters = new MatchQueryFilters(true, null,
                null, null, null, null, null);
        Coordinates userCoordinates = new Coordinates(12.43, 121.1);
        when(matchService.findAllMatchesForUserBasedOnFilter(USER_ID, matchQueryFilters, userCoordinates)).thenReturn(List.of(aMatch));

        UserMatchesDTO actualUserMatches = matchController.findAllMatchesUsingFilters(USER_ID, matchQueryFilters, userCoordinates.getLatitude(), userCoordinates.getLongitude());

        assertThat(actualUserMatches.getMatches())
                .usingFieldByFieldElementComparator()
                .containsAll(expectedMatches);
        assertThat(actualUserMatches.getUserId()).isEqualTo(USER_ID);
    }

    @Test
    public void shouldFilterOutTheMatchesNotInRangeOfAge() {
        Match aMatch = buildBaseMatch(USER_ID)
                .age(19)
                .build();

        List<MatchDTO> expectedMatches = List.of(buildMatchDTO(aMatch));

        MatchQueryFilters matchQueryFilters = new MatchQueryFilters(null, null,
                null, new AgeRange(18, 56), null, null, null);
        Coordinates userCoordinates = new Coordinates(12.34, 13.1);
        when(matchService.findAllMatchesForUserBasedOnFilter(USER_ID, matchQueryFilters, userCoordinates)).thenReturn(List.of(aMatch));

        UserMatchesDTO actualUserMatches = matchController.findAllMatchesUsingFilters(USER_ID, matchQueryFilters,
                userCoordinates.getLatitude(), userCoordinates.getLongitude());

        assertThat(actualUserMatches.getMatches())
                .usingFieldByFieldElementComparator()
                .containsAll(expectedMatches);
        assertThat(actualUserMatches.getUserId()).isEqualTo(USER_ID);
    }
}
