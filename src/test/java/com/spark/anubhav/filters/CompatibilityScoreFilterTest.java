package com.spark.anubhav.filters;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.CompatibilityRange;
import com.spark.anubhav.models.QMatch;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CompatibilityScoreFilterTest {

    @Test
    public void shouldBuildPredicateWhichChecksIfTheCompatibilityScoreOfMatchLiesBetweenGivenValues() {
        BooleanExpression expectedPredicate = QMatch.match.compatibilityScore.between(BigDecimal.ONE, BigDecimal.TEN);

        Predicate actualPredicate = new CompatibilityScoreFilter(new CompatibilityRange(BigDecimal.ONE, BigDecimal.TEN))
                .buildPredicate();

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

}
