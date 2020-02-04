package com.spark.anubhav.utils;

import com.spark.anubhav.models.City;
import com.spark.anubhav.models.DTOs.CityDTO;
import com.spark.anubhav.models.Match;
import com.spark.anubhav.models.DTOs.MatchDTO;

import java.math.BigDecimal;
import java.util.UUID;

public class TestUtils {

    public static Match buildMatch(UUID userId) {
        return Match.builder()
                .age(34)
                .city(new City("cityName", 78.8, 79.9))
                .compatibilityScore(BigDecimal.valueOf(54.34))
                .height(170)
                .displayName("Cool Name")
                .favourite(false)
                .jobTitle("Job1")
                .mainPhoto("")
                .numberOfContactsExchanged(0)
                .religion("Athiest")
                .userId(userId)
                .build();
    }

    public static MatchDTO buildMatchDTO(Match match) {
        City matchCity = match.getCity();
        CityDTO cityDTO = new CityDTO(
                matchCity.getName(),
                matchCity.getLongitude(),
                matchCity.getLatitude());

        return MatchDTO.builder()
                .displayName(match.getDisplayName())
                .age(match.getAge())
                .city(cityDTO)
                .compatibilityScore(match.getCompatibilityScore())
                .favourite(match.getFavourite())
                .heightInCm(match.getHeight())
                .id(match.getId())
                .jobTitle(match.getJobTitle())
                .mainPhoto(match.getMainPhoto())
                .contactsExchanged(match.getNumberOfContactsExchanged())
                .religion(match.getReligion())
                .build();
    }
}
