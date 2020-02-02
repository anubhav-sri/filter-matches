package com.spark.anubhav.repositories;

import com.spark.anubhav.models.Match;

import java.util.List;
import java.util.UUID;

public interface MatchRepository {

    List<Match> findAllByUserId(UUID userId);
}
