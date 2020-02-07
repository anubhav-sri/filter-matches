package com.spark.anubhav.filters;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PredicateBuilder {

    private List<Filter> filterList = new ArrayList<>();

    private PredicateBuilder() {

    }

    public static PredicateBuilder builder() {
        return new PredicateBuilder();
    }

    public PredicateBuilder forUser(UUID userId) {
        filterList.add(new UserIdFilter(userId));
        return this;
    }

    public PredicateBuilder hasPhoto(boolean hasPhoto) {
        filterList.add(new PhotoFilter(hasPhoto));
        return this;
    }

    public Predicate build() {
        return filterList
                .stream()
                .map(Filter::buildPredicate)
                .reduce(BooleanExpression::and).orElseThrow();
    }
}
