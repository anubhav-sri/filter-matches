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
    private AgeRange ageRange;

    public MatchQueryFilters(Boolean hasPhoto, Boolean isFavorite, CompatibilityRange compatibilityRange, AgeRange ageRange) {
        this.hasPhoto = hasPhoto;
        this.isFavorite = isFavorite;
        this.compatibilityRange = compatibilityRange;
        this.ageRange = ageRange;
    }
}
