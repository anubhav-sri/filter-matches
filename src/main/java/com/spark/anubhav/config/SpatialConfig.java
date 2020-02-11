package com.spark.anubhav.config;

import com.vividsolutions.jts.geom.GeometryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpatialConfig {

    @Bean
    public GeometryFactory geometryFactory() {
        return new GeometryFactory();
    }

}
