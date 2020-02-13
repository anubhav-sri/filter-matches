package com.spark.anubhav.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.anubhav.models.City;
import com.spark.anubhav.models.DTOs.MatchDTO;
import com.spark.anubhav.models.DTOs.UserMatchesDTO;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.repositories.MatchRepository;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.spark.anubhav.utils.TestUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MatchIntegrationTest {

    private static final String GET_USER_MATCHES_STRING_TEMPLATE = "/users/%s/matches";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GeometryFactory geometryFactory;

    @Autowired
    private MatchRepository matchRepository;
    @LocalServerPort
    int port;
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

        MockHttpServletRequestBuilder requestBuilder = get(String.format(GET_USER_MATCHES_STRING_TEMPLATE, USER_ID));
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

        MockHttpServletRequestBuilder requestBuilder = get(String.format(GET_USER_MATCHES_STRING_TEMPLATE, USER_ID));
        requestBuilder.param("isFavorite", String.valueOf(true));

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedUserMatches)));

    }

    @Test
    void shouldFilterOutTheNonCompatibleMatchesForTheUser() throws Exception {
        Match compatibleMatches = buildBaseMatch(USER_ID)
                .id(UUID.randomUUID())
                .compatibilityScore(BigDecimal.valueOf(0.98))
                .build();
        Match nonCompatibleMatches = buildBaseMatch(USER_ID)
                .id(UUID.randomUUID())
                .compatibilityScore(BigDecimal.valueOf(0.67))
                .build();

        matchRepository.save(compatibleMatches);
        matchRepository.save(nonCompatibleMatches);

        MatchDTO expectedMatch = buildMatchDTO(compatibleMatches);
        UserMatchesDTO expectedUserMatches = new UserMatchesDTO(USER_ID, List.of(expectedMatch));

        MockHttpServletRequestBuilder requestBuilder = get(String.format(GET_USER_MATCHES_STRING_TEMPLATE, USER_ID));
        requestBuilder.param("compatibilityRange.from", "0.69");
        requestBuilder.param("compatibilityRange.to", "0.99");

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedUserMatches)));

    }

    @Test
    void shouldFilterOutTheMatchesNotLyingInGivenAgeRange() throws Exception {
        Match compatibleMatches = buildBaseMatch(USER_ID)
                .id(UUID.randomUUID())
                .age(45)
                .build();
        Match nonCompatibleMatches = buildBaseMatch(USER_ID)
                .id(UUID.randomUUID())
                .age(49)
                .build();

        matchRepository.save(compatibleMatches);
        matchRepository.save(nonCompatibleMatches);

        MatchDTO expectedMatch = buildMatchDTO(compatibleMatches);
        UserMatchesDTO expectedUserMatches = new UserMatchesDTO(USER_ID, List.of(expectedMatch));

        MockHttpServletRequestBuilder requestBuilder = get(String.format(GET_USER_MATCHES_STRING_TEMPLATE, USER_ID));
        requestBuilder.param("ageRange.from", "40");
        requestBuilder.param("ageRange.to", "46");

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedUserMatches)));

    }

    @Test
    void shouldFilterOutTheMatchesNotInTheDistanceRange() throws Exception {
        Match compatibleMatches = buildBaseMatch(USER_ID)
                .id(UUID.randomUUID())
                .city(new City("MyCity", geometryFactory.createPoint(new Coordinate(12, 34))))
                .build();
        Match nonCompatibleMatches = buildBaseMatch(USER_ID)
                .id(UUID.randomUUID())
                .city(new City("MyCity1", geometryFactory.createPoint(new Coordinate(102, 304))))
                .build();

        matchRepository.save(compatibleMatches);
        matchRepository.save(nonCompatibleMatches);

        MatchDTO expectedMatch = buildMatchDTO(compatibleMatches);
        UserMatchesDTO expectedUserMatches = new UserMatchesDTO(USER_ID, List.of(expectedMatch));

        MockHttpServletRequestBuilder requestBuilder = get(String.format(GET_USER_MATCHES_STRING_TEMPLATE, USER_ID));
        requestBuilder.param("withInDistanceInKms", "30");
        requestBuilder.header("latitude", "12");
        requestBuilder.header("longitude", "34");

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
