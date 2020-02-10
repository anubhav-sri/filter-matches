package com.spark.anubhav.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.anubhav.models.City;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.services.MatchService;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InitialDataLoaderTest {

    @Mock
    private MatchService matchService;

    private final UUID USER_ID = UUID.fromString("31eed42a-fdd1-4751-bcf3-00a4c8e40d7e");
    private Match FIRST_MATCH_EXPECTED = buildFirstMatch();
    private Match SECOND_MATCH_EXPECTED = buildSecondMatch();
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        FIRST_MATCH_EXPECTED = buildFirstMatch();
        SECOND_MATCH_EXPECTED = buildSecondMatch();
    }

    @Test
    public void shouldLoadMatchesFromJsonWhenEnabled() throws IOException {
        List<Match> expectedMatches = List.of(FIRST_MATCH_EXPECTED, SECOND_MATCH_EXPECTED);

        when(matchService.addMatchesForUser(expectedMatches)).thenReturn(expectedMatches);

        List<Match> savedMatches = new InitialDataLoader(matchService, "test_matches.json",
                true, objectMapper).loadMatchesToDB();

        verify(matchService).addMatchesForUser(expectedMatches);

        assertAll("verify if all the matches in json are loaded",
                () -> assertThat(savedMatches.size()).isEqualTo(2),
                () -> assertThat(savedMatches)
                        .usingElementComparatorIgnoringFields("id")
                        .containsAll(expectedMatches));
    }

    @Test
    public void shouldNotLoadMatchesFromJsonWhenDisabled() throws IOException {
        List<Match> matchDTOS = new InitialDataLoader(matchService, "test_matches.json", false, objectMapper).loadMatchesToDB();

        verifyNoInteractions(matchService);

        assertThat(matchDTOS).isEmpty();
    }

    private Match buildSecondMatch() {
        Point locationPoint = new GeometryFactory()
                .createPoint(new Coordinate(52.412811, -1.778197));
        return Match.builder()
                .displayName("Sharon")
                .age(47)
                .jobTitle("Doctor")
                .height(161)
                .city(new City("Solihull", locationPoint))
                .numberOfContactsExchanged(0)
                .religion("Islam")
                .favourite(false)
                .mainPhoto(createMainPhotoURL())
                .compatibilityScore(BigDecimal.valueOf(0.97))
                .userId(USER_ID)
                .build();


    }


    private URL createMainPhotoURL() {
        try {
            return new URL("http://thecatapi.com/api/images/get?format=src&type=gif");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private Match buildFirstMatch() {
        Point locationPoint = new GeometryFactory()
                .createPoint(new Coordinate(53.801277, -1.548567));

        return Match.builder()
                .displayName("Caroline")
                .age(41)
                .jobTitle("Corporate Lawyer")
                .height(153)
                .city(new City("Leeds", locationPoint))
                .numberOfContactsExchanged(2)
                .religion("Atheist")
                .favourite(true)
                .mainPhoto(createMainPhotoURL())
                .compatibilityScore(BigDecimal.valueOf(0.76))
                .userId(USER_ID)
                .build();


    }

}
