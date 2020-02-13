package com.spark.anubhav.integrationTests;

import com.spark.anubhav.repositories.MatchRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class QueryParametersValidationTest {

    @Autowired
    private MockMvc mockMvc;

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
    void shouldThrowInValidFilterExceptionIfCompatibilityScoreOutOfBound() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = get(String.format("/users/%s/matches/filter", USER_ID));
        requestBuilder.param("compatibilityRange.from", "0.005");
        requestBuilder.param("compatibilityRange.to", "89");

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldRespondWithBadRequestIfAgeRangeIsOutOfBound() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = get(String.format("/users/%s/matches/filter", USER_ID));
        requestBuilder.param("ageRange.from", "17");
        requestBuilder.param("ageRange.to", "99");

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldRespondWithBadRequestIfDistanceIsLessThan30Km() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(String.format("/users/%s/matches/filter", USER_ID));
        requestBuilder.param("withInDistanceInKms", "28");

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestIfDistanceIsMoreThan300Km() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(String.format("/users/%s/matches/filter", USER_ID));
        requestBuilder.param("withInDistanceInKms", "301");

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestIfQueryParamHeightIsOutOfBounds() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(String.format("/users/%s/matches/filter", USER_ID));
        requestBuilder.param("heightRange.from", "134");
        requestBuilder.param("heightRange.to", "211");

        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @AfterEach
    void tearDown() {
        matchRepository.deleteAll();
    }
}
