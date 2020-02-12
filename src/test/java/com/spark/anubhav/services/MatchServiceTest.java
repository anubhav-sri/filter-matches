package com.spark.anubhav.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.filters.*;
import com.spark.anubhav.models.*;
import com.spark.anubhav.repositories.MatchRepository;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.spark.anubhav.utils.TestUtils.buildBaseMatch;
import static com.spark.anubhav.utils.TestUtils.buildMatch;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {
    @Mock
    private MatchRepository repository;
    @Mock
    private GeometryFactory geometryFactory;
    private MatchService matchService;
    private final Coordinates coordinates = new Coordinates(12.3, 2.34);

    @BeforeEach
    void setUp() {
        matchService = new MatchService(repository, geometryFactory);
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
                new MatchQueryFilters(true, null, null, null, null, null, null), new Coordinates(null, null));

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

        MatchQueryFilters matchQueryFilters = new MatchQueryFilters(null, true, null, null, null, null, null);
        Iterable<Match> matches = matchService.findAllMatchesForUserBasedOnFilter(userId, matchQueryFilters, coordinates);

        verify(repository).findAll(expectedPredicatePassed);
        assertThat(matches).containsExactly(favoriteMatch);

    }

    @Test
    public void shouldBeAbleToGetOnlyCompatibleMatches() {
        UUID userId = UUID.randomUUID();
        Match compatibleMatch = buildBaseMatch(userId)
                .compatibilityScore(BigDecimal.valueOf(0.67))
                .build();

        List<Match> matchesForUser = Collections.singletonList(compatibleMatch);

        CompatibilityRange range = new CompatibilityRange(BigDecimal.valueOf(0.56), BigDecimal.valueOf(0.89));
        BooleanExpression expectedPredicatePassed = new UserIdFilter(userId).buildPredicate()
                .and(new CompatibilityScoreFilter(range).buildPredicate());

        when(repository.findAll(expectedPredicatePassed))
                .thenReturn(matchesForUser);

        MatchQueryFilters matchQueryFilters = new MatchQueryFilters(null, null, range, null, null, null, null);
        Iterable<Match> matches = matchService.findAllMatchesForUserBasedOnFilter(userId, matchQueryFilters, coordinates);

        verify(repository).findAll(expectedPredicatePassed);
        assertThat(matches).containsExactly(compatibleMatch);

    }

    @Test
    public void shouldBeAbleToGetOnlyMatchesLyingInTheAgeRange() {
        UUID userId = UUID.randomUUID();
        Match agedMatch = buildBaseMatch(userId)
                .age(67)
                .build();

        List<Match> matchesForUser = Collections.singletonList(agedMatch);

        AgeRange range = new AgeRange(60, 68);
        BooleanExpression expectedPredicatePassed = new UserIdFilter(userId).buildPredicate()
                .and(new AgeFilter(range).buildPredicate());

        when(repository.findAll(expectedPredicatePassed))
                .thenReturn(matchesForUser);

        MatchQueryFilters matchQueryFilters = new MatchQueryFilters(null,
                null, null, range, null, null, null);
        Iterable<Match> matches = matchService.findAllMatchesForUserBasedOnFilter(userId, matchQueryFilters, coordinates);

        verify(repository).findAll(expectedPredicatePassed);
        assertThat(matches).containsExactly(agedMatch);

    }

    @Test
    public void shouldBeAbleToGetOnlyMatchesLyingInTheHeightRange() {
        UUID userId = UUID.randomUUID();
        Match matchWithInRangeHeight = buildBaseMatch(userId)
                .height(167)
                .build();

        List<Match> matchesForUser = Collections.singletonList(matchWithInRangeHeight);

        HeightRange range = new HeightRange(135, 210);
        BooleanExpression expectedPredicatePassed = new UserIdFilter(userId).buildPredicate()
                .and(new HeightFilter(range).buildPredicate());

        when(repository.findAll(expectedPredicatePassed))
                .thenReturn(matchesForUser);

        MatchQueryFilters matchQueryFilters = new MatchQueryFilters(null,
                null, null, null, range, null, null);
        Iterable<Match> matches = matchService.findAllMatchesForUserBasedOnFilter(userId, matchQueryFilters, coordinates);

        verify(repository).findAll(expectedPredicatePassed);
        assertThat(matches).containsExactly(matchWithInRangeHeight);

    }

    @Test
    public void shouldBeAbleToGetOnlyMatchesWhichAreInContact() {
        UUID userId = UUID.randomUUID();
        Match matchWithInRangeHeight = buildBaseMatch(userId)
                .numberOfContactsExchanged(3)
                .build();

        List<Match> matchesForUser = Collections.singletonList(matchWithInRangeHeight);

        BooleanExpression expectedPredicatePassed = new UserIdFilter(userId).buildPredicate()
                .and(new InContactFilter(true).buildPredicate());

        when(repository.findAll(expectedPredicatePassed))
                .thenReturn(matchesForUser);

        MatchQueryFilters matchQueryFilters = new MatchQueryFilters(null,
                null, null, null, null, true, null);
        Iterable<Match> matches = matchService.findAllMatchesForUserBasedOnFilter(userId, matchQueryFilters, coordinates);

        verify(repository).findAll(expectedPredicatePassed);
        assertThat(matches).containsExactly(matchWithInRangeHeight);

    }

    @Test
    public void shouldBeAbleToGetOnlyMatchesWhichAreInGivenDistanceRange() {
        UUID userId = UUID.randomUUID();
        Match matchWithInRangeHeight = buildBaseMatch(userId)
                .build();

        List<Match> matchesForUser = Collections.singletonList(matchWithInRangeHeight);
        DistanceRange distanceRange = new DistanceRange(12, 12);

        Coordinate coordinate = new Coordinate(coordinates.getLatitude(), coordinates.getLongitude());
        Point point = new GeometryFactory().createPoint(coordinate);
        when(geometryFactory.createPoint(coordinate))
                .thenReturn(point);

        BooleanExpression expectedPredicatePassed = new UserIdFilter(userId).buildPredicate()
                .and(new DistanceFilter(distanceRange, coordinates, geometryFactory).buildPredicate());

        when(repository.findAll(expectedPredicatePassed))
                .thenReturn(matchesForUser);

        MatchQueryFilters matchQueryFilters = new MatchQueryFilters(null,
                null, null, null, null, null, distanceRange);

        Iterable<Match> matches = matchService.findAllMatchesForUserBasedOnFilter(userId, matchQueryFilters, coordinates);

        verify(repository).findAll(expectedPredicatePassed);
        assertThat(matches).containsExactly(matchWithInRangeHeight);

    }

}
