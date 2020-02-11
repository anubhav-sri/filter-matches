package com.spark.anubhav.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;

@Getter
@EqualsAndHashCode
@Setter
@ToString
public class MatchQueryFilters {
    private Boolean hasPhoto;
    private Boolean isFavorite;
    @Valid
    private CompatibilityRange compatibilityRange;
    @Valid
    private AgeRange ageRange;
    private HeightRange heightRange;
    private Boolean inContact;
    private DistanceRange distanceRange;

    public MatchQueryFilters(Boolean hasPhoto, Boolean isFavorite, CompatibilityRange compatibilityRange,
                             AgeRange ageRange, HeightRange heightRange, Boolean inContact, DistanceRange distanceRange) {
        this.hasPhoto = hasPhoto;
        this.isFavorite = isFavorite;
        this.compatibilityRange = compatibilityRange;
        this.ageRange = ageRange;
        this.heightRange = heightRange;
        this.inContact = inContact;
        this.distanceRange = distanceRange;
    }
}
