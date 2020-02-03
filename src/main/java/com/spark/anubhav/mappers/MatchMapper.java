package com.spark.anubhav.mappers;

import com.spark.anubhav.models.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MatchMapper {
    public static UserMatchesDTO convertToDTO(UUID userId, List<Match> allMatchesForUser) {

        List<MatchDTO> matchesDTO = allMatchesForUser
                .stream()
                .map(MatchMapper::buildMatchDTO)
                .collect(Collectors.toList());

        return new UserMatchesDTO(userId, matchesDTO);
    }

    public static Match mapDTOtoMatch(UUID userId, MatchDTO matchDTO) {

        CityDTO city = matchDTO.getCity();
        return Match.builder()
                .displayName(matchDTO.getDisplayName())
                .religion(matchDTO.getReligion())
                .numberOfContactsExchanged(matchDTO.getContactsExchanged())
                .mainPhoto(matchDTO.getMainPhoto())
                .jobTitle(matchDTO.getJobTitle())
                .height(matchDTO.getHeightInCm())
                .favourite(matchDTO.getFavourite())
                .compatibilityScore(matchDTO.getCompatibilityScore())
                .city(new City(city.getName(), city.getLatitude(), city.getLongitude()))
                .age(matchDTO.getAge())
                .userId(userId)
                .build();
    }

    private static MatchDTO buildMatchDTO(Match match) {
        City matchCity = match.getCity();
        CityDTO cityDTO = new CityDTO(
                matchCity.getName(),
                matchCity.getLongitude(),
                matchCity.getLatitude());

        return MatchDTO.builder()
                .name(match.getName())
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
