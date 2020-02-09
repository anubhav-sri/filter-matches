package com.spark.anubhav.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.anubhav.models.DTOs.MatchDTO;
import com.spark.anubhav.models.DTOs.UserMatchesDTO;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.repositories.MatchRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;

import static com.spark.anubhav.utils.TestUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MatchIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MatchRepository matchRepository;
    private static final UUID USER_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        matchRepository.deleteAll();
    }

    @Test
    void shouldGetAllTheMatchesForUser() throws Exception {
        Match match = buildMatch(USER_ID);
        match.setId(UUID.randomUUID());
        matchRepository.save(match);

        UserMatchesDTO expectedUserMatches = new UserMatchesDTO(USER_ID, List.of(buildMatchDTO(match)));

        this.mockMvc.perform(get(String.format("/users/%s/matches", USER_ID)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedUserMatches)));

    }

    @Test
    void shouldFilterTheMatchesForUserBasedOnPresenceOfPhoto() throws Exception {
        Match aMatch = buildMatch(USER_ID);
        aMatch.setId(UUID.randomUUID());

        Match.MatchBuilder matchBuilder = buildBaseMatch(USER_ID);
        Match anotherMatch = matchBuilder.mainPhoto(null)
                .build();
        anotherMatch.setId(UUID.randomUUID());

        matchRepository.save(aMatch);
        matchRepository.save(anotherMatch);

        MatchDTO expectedMatch = buildMatchDTO(aMatch);
        UserMatchesDTO expectedUserMatches = new UserMatchesDTO(USER_ID, List.of(expectedMatch));

        MockHttpServletRequestBuilder requestBuilder = get(String.format("/users/%s/matches/filter", USER_ID));
        requestBuilder.param("hasPhoto", String.valueOf(true));

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedUserMatches)));

    }

    @Test
    void shouldFilterTheNonFavoriteMatchesForTheUser() throws Exception {
        Match favoriteMatch = createFavoriteMatch();
        Match nonFavoriteMatch = createNonFavoriteMatch();

        matchRepository.save(favoriteMatch);
        matchRepository.save(nonFavoriteMatch);

        MatchDTO expectedMatch = buildMatchDTO(favoriteMatch);
        UserMatchesDTO expectedUserMatches = new UserMatchesDTO(USER_ID, List.of(expectedMatch));

        MockHttpServletRequestBuilder requestBuilder = get(String.format("/users/%s/matches/filter", USER_ID));
        requestBuilder.param("isFavorite", String.valueOf(true));

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedUserMatches)));

    }

    private Match createNonFavoriteMatch() {
        return buildBaseMatch(USER_ID)
                .id(UUID.randomUUID())
                .favourite(false)
                .build();
    }

    private Match createFavoriteMatch() {
        return buildBaseMatch(USER_ID)
                .favourite(true)
                .id(UUID.randomUUID())
                .build();
    }

    @AfterEach
    void tearDown() {
        matchRepository.deleteAll();
    }
}
