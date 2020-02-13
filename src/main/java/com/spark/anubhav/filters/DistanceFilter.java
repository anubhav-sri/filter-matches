package com.spark.anubhav.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.spatial.jts.JTSGeometryPath;
import com.spark.anubhav.models.Coordinates;
import com.spark.anubhav.models.QMatch;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class DistanceFilter implements Filter {
    private Integer withInDistanceInKms;
    private final Coordinates coordinate;
    private GeometryFactory geometryFactory;

    public DistanceFilter(Integer withInDistanceInKms, Coordinates coordinate, GeometryFactory geometryFactory) {
        this.withInDistanceInKms = withInDistanceInKms;
        this.coordinate = coordinate;
        this.geometryFactory = geometryFactory;
    }

    public BooleanExpression buildPredicate() {
        ComparablePath<Point> matchLocation = QMatch.match.city.location;
        JTSGeometryPath jtsPathForLocation = new JTSGeometryPath(matchLocation.getMetadata());
        Point usersPointLocation = geometryFactory.createPoint(new Coordinate(coordinate.getLatitude(), coordinate.getLongitude()));

        return jtsPathForLocation.distance(usersPointLocation).loe(withInDistanceInKms);
    }
}
