package com.spark.anubhav.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class MatchDTO implements Serializable {
    private UUID id;
    private String displayName;
    private Integer age;
    private String jobTitle;
    private CityDTO city;
    private Integer heightInCm;
    private String name;
    private String mainPhoto;
    private BigDecimal compatibilityScore;
    private Integer contactsExchanged;
    private Boolean favourite;
    private String religion;

}
