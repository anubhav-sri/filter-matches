package com.spark.anubhav.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

@Getter
@EqualsAndHashCode
@Setter
public class MatchQueryFilters {
    private Boolean hasPhoto;
    private Boolean isFavorite;
    @Valid
    private CompatibilityRange compatibilityRange;
    private AgeRange ageRange;
    private HeightRange heightRange;
    private Boolean inContact;

    public MatchQueryFilters(Boolean hasPhoto, Boolean isFavorite, CompatibilityRange compatibilityRange,
                             AgeRange ageRange, HeightRange heightRange, Boolean inContact) {
        this.hasPhoto = hasPhoto;
        this.isFavorite = isFavorite;
        this.compatibilityRange = compatibilityRange;
        this.ageRange = ageRange;
        this.heightRange = heightRange;
        this.inContact = inContact;
    }
}
