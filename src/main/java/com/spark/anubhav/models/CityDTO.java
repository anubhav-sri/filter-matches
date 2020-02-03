package com.spark.anubhav.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
public class CityDTO implements Serializable {
    private final String name;
    private final double longitude;
    private final double latitude;

}
