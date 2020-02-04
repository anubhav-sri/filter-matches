package com.spark.anubhav.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.models.UserMatchesDTO;
import com.spark.anubhav.repositories.MatchRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static com.spark.anubhav.utils.TestUtils.buildMatch;
import static com.spark.anubhav.utils.TestUtils.buildMatchDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MatchIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MatchRepository matchRepository;
    private static final UUID USER_ID = UUID.randomUUID();

    @Test
    public void shouldGetAllTheMatchesForUser() throws Exception {
        Match match = buildMatch(USER_ID);
        match.setId(UUID.randomUUID());
        matchRepository.save(match);

        UserMatchesDTO expectedUserMatches = new UserMatchesDTO(USER_ID, List.of(buildMatchDTO(match)));

        this.mockMvc.perform(get(String.format("/users/%s/matches", USER_ID)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedUserMatches)));

    }

}
