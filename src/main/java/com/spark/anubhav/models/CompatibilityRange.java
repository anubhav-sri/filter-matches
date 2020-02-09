package com.spark.anubhav.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CompatibilityRange {
    private BigDecimal from;
    private BigDecimal to;
}
