package com.spark.anubhav.controllers;

import com.spark.anubhav.mappers.MatchMapper;
import com.spark.anubhav.models.UserMatchesDTO;
import com.spark.anubhav.services.MatchService;

import java.util.UUID;

public class MatchController {
    private MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    public UserMatchesDTO getAllMatchesForUser(UUID userId) {
        return MatchMapper.convertToDTO(userId, matchService.findAllMatchesForUser(userId));
    }
}
