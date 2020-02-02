package com.spark.anubhav.services;

import com.spark.anubhav.models.City;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.repositories.MatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {
    @Mock
    private MatchRepository repository;

    @Test
    public void shouldGetAllTheMatches() {
        UUID userId = UUID.randomUUID();
        Match aMatch = buildMatch(userId);
        when(repository.findAllByUserId(userId)).thenReturn(Collections.singletonList(aMatch));
        List<Match> matches = new MatchService(repository).findAllMatchesForUser(userId);
        assertThat(matches).containsExactly(aMatch);
    }

    private Match buildMatch(UUID userId) {
        return Match.builder()
                .id(UUID.randomUUID())
                .age(34)
                .city(new City("cityName", 78.8, 79.9))
                .compatibilityScore(BigDecimal.valueOf(54))
                .height(170)
                .displayName("Cool Name")
                .favourite(false)
                .name("Name")
                .jobTitle("Job1")
                .mainPhoto("")
                .numberOfContactsExchanged(0)
                .religion("Athiest")
                .userId(userId)
                .build();
    }

}
