package com.spark.anubhav.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class City {
    @Id
    private String name;
    private double latitude;
    private double longitude;
}
