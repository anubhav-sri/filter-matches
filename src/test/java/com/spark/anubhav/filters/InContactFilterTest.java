package com.spark.anubhav.filters;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.QMatch;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InContactFilterTest {
    @Test
    public void shouldBuildPredicateWhichChecksIfAnyContactsExchangedInThePastWithUser() {
        BooleanExpression expectedPredicate = QMatch.match.numberOfContactsExchanged.gt(0);

        Predicate actualPredicate = new InContactFilter(true)
                .buildPredicate();

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldBuildPredicateWhichChecksIfNoContactsExchangedInThePastWithUser() {
        BooleanExpression expectedPredicate = QMatch.match.numberOfContactsExchanged.eq(0);

        Predicate actualPredicate = new InContactFilter(false)
                .buildPredicate();

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }
}
