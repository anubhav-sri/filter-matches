package com.spark.anubhav.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.QMatch;

public class FavouriteFilter implements Filter {
    private Boolean isFavorite;

    public FavouriteFilter(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public BooleanExpression buildPredicate() {
        return isFavorite ? QMatch.match.favourite.isTrue() : QMatch.match.favourite.isFalse();
    }
}
