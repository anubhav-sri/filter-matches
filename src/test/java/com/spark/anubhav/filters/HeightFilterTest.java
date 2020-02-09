package com.spark.anubhav.filters;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.HeightRange;
import com.spark.anubhav.models.QMatch;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeightFilterTest {
    @Test
    public void shouldBuildPredicateWhichChecksIfHeightOfMatchLiesBetweenGivenValues() {
        BooleanExpression expectedPredicate = QMatch.match.height.between(135, 210);

        Predicate actualPredicate = new HeightFilter(new HeightRange(135, 210))
                .buildPredicate();

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

}
