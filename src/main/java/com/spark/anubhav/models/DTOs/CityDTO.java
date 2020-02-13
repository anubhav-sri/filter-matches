package com.spark.anubhav.models.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class CityDTO implements Serializable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("lat")
    private Double latitude;
    @JsonProperty("lon")
    private Double longitude;

}
