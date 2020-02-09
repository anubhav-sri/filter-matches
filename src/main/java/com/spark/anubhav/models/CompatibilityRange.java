package com.spark.anubhav.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CompatibilityRange {
    private BigDecimal from;
    private BigDecimal to;
}
