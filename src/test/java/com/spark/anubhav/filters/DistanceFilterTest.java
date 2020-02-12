package com.spark.anubhav.filters;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.spatial.jts.JTSGeometryPath;
import com.spark.anubhav.models.Coordinates;
import com.spark.anubhav.models.DistanceRange;
import com.spark.anubhav.models.QMatch;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DistanceFilterTest {

    @Mock
    private GeometryFactory geometryFactory;
    private DistanceFilter distanceFilter;
    private DistanceRange distanceRange;

    @BeforeEach
    void setUp() {
        double usersLatitude = 12;
        double usersLongitude = 12;
        distanceRange = new DistanceRange(18, 95);
        distanceFilter = new DistanceFilter(distanceRange, new Coordinates(usersLatitude, usersLongitude), geometryFactory);
    }

    @Test
    public void shouldBuildPredicateWhichChecksIfTheMatchStaysInGivenDistanceRange() {
        double usersLatitude = 12;
        double usersLongitude = 12;
        ComparablePath<Point> location = QMatch.match.city.location;
        JTSGeometryPath geometryPath = new JTSGeometryPath(location.getMetadata());

        Point point = mock(Point.class);

        when(geometryFactory.createPoint(new Coordinate(usersLatitude, usersLongitude))).thenReturn(point);

        BooleanExpression predicateToConsiderMatchesDistantLessThanUpperLimit = geometryPath.distance(point).loe(distanceRange.getTo());
        BooleanExpression predicateToConsiderMatchesDistantGreaterThanUpperLimit = geometryPath.distance(point).goe(distanceRange.getFrom());

        BooleanExpression expectedPredicate = predicateToConsiderMatchesDistantLessThanUpperLimit
                .and(predicateToConsiderMatchesDistantGreaterThanUpperLimit);

        Predicate actualPredicate = distanceFilter
                .buildPredicate();

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }
}
