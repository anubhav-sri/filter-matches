package com.spark.anubhav.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.QMatch;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PhotoFilterTest {

    @Test
    public void shouldBuildPredicateWhichChecksForThePresenceOfPhotoIfHasPhotoIsTrue() {
        BooleanExpression booleanExpression = new PhotoFilter(true).buildPredicate();
        assertThat(booleanExpression).isEqualTo(QMatch.match.mainPhoto.isNotNull());
    }

    @Test
    public void shouldBuildPredicateWhichChecksForTheAbsenceOfPhotoIfHasPhotoIsFalse() {
        BooleanExpression booleanExpression = new PhotoFilter(false).buildPredicate();
        assertThat(booleanExpression).isEqualTo(QMatch.match.mainPhoto.isNull());
    }
}
