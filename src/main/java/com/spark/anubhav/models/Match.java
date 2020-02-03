package com.spark.anubhav.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
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

}
