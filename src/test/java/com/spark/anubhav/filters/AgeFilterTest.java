package com.spark.anubhav.filters;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.AgeRange;
import com.spark.anubhav.models.QMatch;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AgeFilterTest {
    @Test
    public void shouldBuildPredicateWhichChecksIfAgeOfMatchLiesBetweenGivenValues() {
        BooleanExpression expectedPredicate = QMatch.match.age.between(18, 95);

        Predicate actualPredicate = new AgeFilter(new AgeRange(18, 95))
                .buildPredicate();

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

}
