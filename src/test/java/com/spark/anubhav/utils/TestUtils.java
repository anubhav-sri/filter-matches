package com.spark.anubhav.utils;

import com.spark.anubhav.models.City;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.models.MatchDTO;

import java.math.BigDecimal;
import java.util.UUID;

public class TestUtils {

    public static Match buildMatch(UUID userId) {
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

    public static MatchDTO buildMatchDTO(Match match) {
        return MatchDTO.builder()
                .name(match.getName())
                .displayName(match.getDisplayName())
                .age(match.getAge())
                .city(match.getCity())
                .compatibilityScore(match.getCompatibilityScore())
                .favourite(match.getFavourite())
                .height(match.getHeight())
                .id(match.getId())
                .jobTitle(match.getJobTitle())
                .mainPhoto(match.getMainPhoto())
                .contactsExchanged(match.getNumberOfContactsExchanged())
                .religion(match.getReligion())
                .build();
    }
}
