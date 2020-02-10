package com.spark.anubhav.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.spatial.jts.JTSGeometryPath;
import com.spark.anubhav.models.DistanceRange;
import com.spark.anubhav.models.QMatch;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class DistanceFilter implements Filter {
    private final DistanceRange range;
    private final double latitude;
    private final double longitude;
    private GeometryFactory geometryFactory;

    public DistanceFilter(DistanceRange distanceRange, double latitude, double longitude, GeometryFactory geometryFactory) {
        range = distanceRange;
        this.latitude = latitude;
        this.longitude = longitude;
        this.geometryFactory = geometryFactory;
    }

    public BooleanExpression buildPredicate() {
        ComparablePath<Point> matchLocation = QMatch.match.city.location;
        JTSGeometryPath jtsPathForLocation = new JTSGeometryPath(matchLocation.getMetadata());
        Point usersPointLocation = geometryFactory.createPoint(new Coordinate(latitude, longitude));

        BooleanExpression predicateToConsiderMatchesDistantLessThanUpperLimit = jtsPathForLocation.distance(usersPointLocation).loe(range.getTo());
        BooleanExpression predicateToConsiderMatchesDistantGreaterThanUpperLimit = jtsPathForLocation.distance(usersPointLocation).goe(range.getFrom());

        return predicateToConsiderMatchesDistantLessThanUpperLimit
                .and(predicateToConsiderMatchesDistantGreaterThanUpperLimit);
    }
}
