package com.spark.anubhav.repositories;

import com.spark.anubhav.models.Match;

import java.util.List;

public interface MatchRepository {

    List<Match> findAllByUserId(String userId);
}
