package com.spark.anubhav.filters;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.CompatibilityRange;

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

    public PredicateBuilder hasPhoto(Boolean hasPhoto) {
        if (hasPhoto != null) {
            filterList.add(new PhotoFilter(hasPhoto));
        }
        return this;
    }

    public PredicateBuilder isFavorite(Boolean isFavorite) {
        if (isFavorite != null)
            filterList.add(new FavouriteFilter(isFavorite));
        return this;
    }

    public PredicateBuilder withCompatibility(CompatibilityRange compatibilityRange) {
        if (compatibilityRange != null) {
            this.filterList.add(new CompatibilityScoreFilter(compatibilityRange));
        }
        return this;
    }

    public Predicate build() {
        return filterList
                .stream()
                .map(Filter::buildPredicate)
                .reduce(BooleanExpression::and).orElseThrow();
    }
}
