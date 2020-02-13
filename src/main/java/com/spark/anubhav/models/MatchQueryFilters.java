package com.spark.anubhav.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
    @Min(30)
    @Max(300)
    private Integer withInDistanceInKms;

    public MatchQueryFilters(Boolean hasPhoto, Boolean isFavorite, CompatibilityRange compatibilityRange,
                             AgeRange ageRange, HeightRange heightRange, Boolean inContact, Integer withInDistanceInKms) {
        this.hasPhoto = hasPhoto;
        this.isFavorite = isFavorite;
        this.compatibilityRange = compatibilityRange;
        this.ageRange = ageRange;
        this.heightRange = heightRange;
        this.inContact = inContact;
        this.withInDistanceInKms = withInDistanceInKms;
    }
}
