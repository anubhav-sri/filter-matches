package com.spark.anubhav.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.exceptions.UserIdCannotBeNullException;
import com.spark.anubhav.models.QMatch;

import javax.annotation.Nonnull;
import java.util.UUID;

public class UserIdFilter implements Filter {
    private UUID userId;

    public UserIdFilter(@Nonnull UUID userId) {
        this.userId = userId;
    }

    public BooleanExpression buildPredicate() {
        if (this.userId == null)
            throw new UserIdCannotBeNullException();

        return QMatch.match.userId.eq(userId);
    }
}
