package com.spark.anubhav.repositories;

import com.spark.anubhav.models.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchRepository extends CrudRepository<Match, UUID> {

    List<Match> findAllByUserId(UUID userId);

    default List<Match> findAllByFiltersForUser(UUID userId, boolean hasPhoto) {
        throw new UnsupportedOperationException();
    }
}
