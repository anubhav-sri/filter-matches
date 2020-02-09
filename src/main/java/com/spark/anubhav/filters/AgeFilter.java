package com.spark.anubhav.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.AgeRange;
import com.spark.anubhav.models.QMatch;

public class AgeFilter implements Filter {
    private AgeRange range;

    public AgeFilter(AgeRange range) {
        this.range = range;
    }

    public BooleanExpression buildPredicate() {
        return QMatch.match.age.between(range.getFrom(), range.getTo());
    }
}
