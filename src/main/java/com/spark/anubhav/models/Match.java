package com.spark.anubhav.models;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public class Match {
    private UUID id;
    private String displayName;
    private Integer age;
    private String jobTitle;
    private City city;
    private Integer height;
    private String name;
    private String mainPhoto;
    private BigDecimal compatibilityScore;
    private Integer numberOfContactsExchanged;
    private Boolean favourite;
    private String religion;
    private UUID userId;

    public Match(UUID id, String displayName, Integer age, String jobTitle, City city, Integer height, String name, String mainPhoto, BigDecimal compatibilityScore, Integer numberOfContactsExchanged, Boolean favourite, String religion, UUID userId) {
        this.id = id;
        this.displayName = displayName;
        this.age = age;
        this.jobTitle = jobTitle;
        this.city = city;
        this.height = height;
        this.name = name;
        this.mainPhoto = mainPhoto;
        this.compatibilityScore = compatibilityScore;
        this.numberOfContactsExchanged = numberOfContactsExchanged;
        this.favourite = favourite;
        this.religion = religion;
        this.userId = userId;
    }
}
