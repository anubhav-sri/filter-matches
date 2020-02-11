package com.spark.anubhav.controllers;

import com.spark.anubhav.mappers.MatchMapper;
import com.spark.anubhav.models.Coordinates;
import com.spark.anubhav.models.DTOs.UserMatchesDTO;
import com.spark.anubhav.models.MatchQueryFilters;
import com.spark.anubhav.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
                                                     @Valid MatchQueryFilters queryFilters,
                                                     @RequestHeader(value = "latitude", required = false) Double latitude,
                                                     @RequestHeader(value = "longitude", required = false) Double longitude) {
        return MatchMapper.convertToDTO(userId,
                matchService.findAllMatchesForUserBasedOnFilter(userId,
                        queryFilters, new Coordinates(latitude, longitude)));
    }
}
