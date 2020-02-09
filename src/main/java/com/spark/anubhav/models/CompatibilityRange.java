package com.spark.anubhav.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CompatibilityRange {
    @DecimalMin(value = "0.01")
    private BigDecimal from;
    @DecimalMax(value = "1")
    private BigDecimal to;
}
