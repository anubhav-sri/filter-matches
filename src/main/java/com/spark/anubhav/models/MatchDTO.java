package com.spark.anubhav.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class MatchDTO {
    private UUID id;
    private String displayName;
    private Integer age;
    private String jobTitle;
    private CityDTO city;
    private Integer height;
    private String name;
    private String mainPhoto;
    private BigDecimal compatibilityScore;
    private Integer contactsExchanged;
    private Boolean favourite;
    private String religion;

}
