package com.spark.anubhav.models;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class UserMatchesDTO {
    private final UUID userId;
    private final List<MatchDTO> matches;

    public UserMatchesDTO(UUID userId, List<MatchDTO> matches) {
        this.userId = userId;
        this.matches = matches;
    }
}
