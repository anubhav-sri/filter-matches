package com.spark.anubhav.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.CompatibilityRange;
import com.spark.anubhav.models.QMatch;

public class CompatibilityScoreFilter implements Filter {
    private CompatibilityRange range;

    public CompatibilityScoreFilter(CompatibilityRange range) {
        this.range = range;
    }

    public BooleanExpression buildPredicate() {
        return QMatch.match.compatibilityScore.between(range.getFrom(), range.getTo());
    }
}
