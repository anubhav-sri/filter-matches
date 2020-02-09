package com.spark.anubhav.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.HeightRange;
import com.spark.anubhav.models.QMatch;

public class HeightFilter implements Filter {
    private HeightRange range;

    public HeightFilter(HeightRange range) {
        this.range = range;
    }

    public BooleanExpression buildPredicate() {
        return QMatch.match.height.between(range.getFrom(), range.getTo());
    }
}
