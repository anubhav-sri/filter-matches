package com.spark.anubhav.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.filters.FavouriteFilter;
import com.spark.anubhav.filters.PhotoFilter;
import com.spark.anubhav.filters.UserIdFilter;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.models.MatchQueryFilters;
import com.spark.anubhav.repositories.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.spark.anubhav.utils.TestUtils.buildBaseMatch;
import static com.spark.anubhav.utils.TestUtils.buildMatch;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

    @Test
    public void shouldBeAbleToFilterOutTheMatchIfPhotoIsMissing() {
        UUID userId = UUID.randomUUID();
        Match aMatch = buildMatch(userId);
        List<Match> matchesForUser = Collections.singletonList(aMatch);

        BooleanExpression predicatedPassed = new UserIdFilter(userId).buildPredicate()
                .and(new PhotoFilter(true).buildPredicate());

        when(repository.findAll(predicatedPassed))
                .thenReturn(matchesForUser);

        Iterable<Match> matches = matchService.findAllMatchesForUserBasedOnFilter(userId,
                new MatchQueryFilters(true, null));

        verify(repository).findAll(predicatedPassed);
        assertThat(matches).containsExactly(aMatch);

    }

    @Test
    public void shouldBeAbleToGetOnlyFavoriteMatch() {
        UUID userId = UUID.randomUUID();
        Match favoriteMatch = buildBaseMatch(userId)
                .favourite(true)
                .build();

        List<Match> matchesForUser = Collections.singletonList(favoriteMatch);

        BooleanExpression expectedPredicatePassed = new UserIdFilter(userId).buildPredicate()
                .and(new FavouriteFilter(true).buildPredicate());

        when(repository.findAll(expectedPredicatePassed))
                .thenReturn(matchesForUser);

        Iterable<Match> matches = matchService.findAllMatchesForUserBasedOnFilter(userId, new MatchQueryFilters(null, true));

        verify(repository).findAll(expectedPredicatePassed);
        assertThat(matches).containsExactly(favoriteMatch);

    }

}
