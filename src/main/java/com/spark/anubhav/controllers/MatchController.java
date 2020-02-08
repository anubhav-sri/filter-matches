package com.spark.anubhav.controllers;

import com.spark.anubhav.mappers.MatchMapper;
import com.spark.anubhav.models.DTOs.UserMatchesDTO;
import com.spark.anubhav.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MatchController {
    private MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping(value = "/users/{userId}/matches")
    public UserMatchesDTO getAllMatchesForUser(@PathVariable("userId") UUID userId) {
        return MatchMapper.convertToDTO(userId, matchService.findAllMatchesForUser(userId));
    }

    @GetMapping(value = "/users/{userId}/matches/filter")
    public UserMatchesDTO filterOutTheMatchesFotUser(@PathVariable UUID userId,
                                                     @RequestParam(required = false) Boolean hasPhoto) {
        return MatchMapper.convertToDTO(userId, matchService.findAllMatchesForUserBasedOnFilter(userId, hasPhoto));
    }
}
