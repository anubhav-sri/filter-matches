package com.spark.anubhav.repositories;

import com.spark.anubhav.models.Match;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchRepository extends CrudRepository<Match, UUID>, QuerydslPredicateExecutor<Match> {

    List<Match> findAllByUserId(UUID userId);
}
