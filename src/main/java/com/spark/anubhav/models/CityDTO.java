package com.spark.anubhav.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class CityDTO {
    private final String name;
    private final double longitude;
    private final double latitude;

}
