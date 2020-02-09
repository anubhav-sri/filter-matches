package com.spark.anubhav.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@Setter
public class MatchQueryFilters {
    private Boolean hasPhoto;
    private Boolean isFavorite;
    private CompatibilityRange compatibilityRange;

    public MatchQueryFilters(Boolean hasPhoto, Boolean isFavorite, CompatibilityRange compatibilityRange) {
        this.hasPhoto = hasPhoto;
        this.isFavorite = isFavorite;
        this.compatibilityRange = compatibilityRange;
    }
}
