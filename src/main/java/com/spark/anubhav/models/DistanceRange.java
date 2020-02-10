package com.spark.anubhav.models;

import lombok.Getter;

@Getter
public class DistanceRange {
    private final Integer from;
    private final Integer to;

    public DistanceRange(Integer from, Integer to) {

        this.from = from;
        this.to = to;
    }
}
