package com.spark.anubhav.models;

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
    @JsonProperty("lon")
    private Double longitude;
    @JsonProperty("lat")
    private Double latitude;

}
