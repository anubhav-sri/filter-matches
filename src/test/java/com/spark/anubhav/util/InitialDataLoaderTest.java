package com.spark.anubhav.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.anubhav.models.City;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.repositories.MatchRepository;
import com.spark.anubhav.services.MatchService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Equals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InitialDataLoaderTest {

    @Mock
    private MatchService matchService;

    private final UUID USER_ID = UUID.fromString("31eed42a-fdd1-4751-bcf3-00a4c8e40d7e");
    private final Match FIRST_MATCH_EXPECTED = buildFirstMatch();
    private final Match SECOND_MATCH_EXPECTED = buildSecondMatch();
    private ObjectMapper objectMapper = new ObjectMapper();

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
        return Match.builder()
                .displayName("Sharon")
                .age(47)
                .jobTitle("Doctor")
                .height(161)
                .city(new City("Solihull", 52.412811, -1.778197))
                .numberOfContactsExchanged(0)
                .religion("Islam")
                .favourite(false)
                .mainPhoto("http://thecatapi.com/api/images/get?format=src&type=gif")
                .compatibilityScore(BigDecimal.valueOf(0.97))
                .userId(USER_ID)
                .build();


    }

    private Match buildFirstMatch() {
        return Match.builder()
                .displayName("Caroline")
                .age(41)
                .jobTitle("Corporate Lawyer")
                .height(153)
                .city(new City("Leeds", 53.801277, -1.548567))
                .numberOfContactsExchanged(2)
                .religion("Atheist")
                .favourite(true)
                .mainPhoto("http://thecatapi.com/api/images/get?format=src&type=gif")
                .compatibilityScore(BigDecimal.valueOf(0.76))
                .userId(USER_ID)
                .build();


    }

}
