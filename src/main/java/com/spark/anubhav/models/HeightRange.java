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
public class HeightRange {
    @Min(135)
    @Max(210)
    private Integer from;
    @Min(135)
    @Max(210)
    private Integer to;
}
