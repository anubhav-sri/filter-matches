package com.spark.anubhav.models;

import com.vividsolutions.jts.geom.Point;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class City {
    @Id
    private String name;
    @Column(columnDefinition = "geometry")
    private Point location;
}
