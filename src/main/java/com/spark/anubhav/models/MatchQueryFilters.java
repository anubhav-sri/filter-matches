package com.spark.anubhav.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MatchQueryFilters {
    private final Boolean hasPhoto;
    private final Boolean isFavorite;

    public MatchQueryFilters(Boolean hasPhoto, Boolean isFavorite) {
        this.hasPhoto = hasPhoto;
        this.isFavorite = isFavorite;
    }
}
