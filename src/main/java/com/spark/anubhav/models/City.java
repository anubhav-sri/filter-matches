package com.spark.anubhav.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class City {
    private final String name;
    private final double latitude;
    private final double longitude;
}
