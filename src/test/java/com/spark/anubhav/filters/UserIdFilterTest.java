package com.spark.anubhav.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.exceptions.UserIdCannotBeNullException;
import com.spark.anubhav.models.QMatch;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserIdFilterTest {

    @Test
    public void shouldReturnPredicateWhichFiltersTheMatchesForTheGivenUser() {
        UUID userId = UUID.randomUUID();

        BooleanExpression userIdFilter = new UserIdFilter(userId).buildPredicate();

        BooleanExpression expectedPredicate = QMatch.match.userId.eq(userId);

        assertThat(userIdFilter).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldThrowUserIdCannotBeNullExceptionIfNoUserIdIsProvided() {
        Filter userIdFilter = new UserIdFilter(null);

        assertThrows(UserIdCannotBeNullException.class,
                userIdFilter::buildPredicate);
    }
}
