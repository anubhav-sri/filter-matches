package com.spark.anubhav.filters;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.QMatch;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FavouriteFilterTest {

    @Test
    public void shouldBuildPredicateWhichChecksForIfTheMatchIsFavorite() {
        BooleanExpression expectedPredicate = QMatch.match.favourite.isTrue();

        Predicate actualPredicate = new FavouriteFilter(true).buildPredicate();

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldBuildPredicateWhichChecksForIfTheMatchIsNotFavorite() {
        BooleanExpression expectedPredicate = QMatch.match.favourite.isFalse();

        Predicate actualPredicate = new FavouriteFilter(false).buildPredicate();

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

}
