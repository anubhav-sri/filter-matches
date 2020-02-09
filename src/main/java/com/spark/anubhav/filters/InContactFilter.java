package com.spark.anubhav.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.QMatch;

public class InContactFilter implements Filter {
    private boolean inContact;

    public InContactFilter(boolean inContact) {
        this.inContact = inContact;
    }

    public BooleanExpression buildPredicate() {
        if (inContact) {
            return QMatch.match.numberOfContactsExchanged.gt(0);
        }
        return QMatch.match.numberOfContactsExchanged.eq(0);
    }
}
