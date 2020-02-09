package com.spark.anubhav.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AgeRange {
    @Min(18L)
    private Integer from;
    @Max(95)
    private Integer to;
}
