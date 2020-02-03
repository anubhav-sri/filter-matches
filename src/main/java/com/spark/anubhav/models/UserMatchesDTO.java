package com.spark.anubhav.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserMatchesDTO {
    private UUID userId;
    private List<MatchDTO> matches;

    public UserMatchesDTO(UUID userId, List<MatchDTO> matches) {
        this.userId = userId;
        this.matches = matches;
    }
}
