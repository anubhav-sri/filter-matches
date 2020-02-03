package com.spark.anubhav.services;

import com.spark.anubhav.models.Match;
import com.spark.anubhav.repositories.MatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.spark.anubhav.utils.TestUtils.buildMatch;
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

}
