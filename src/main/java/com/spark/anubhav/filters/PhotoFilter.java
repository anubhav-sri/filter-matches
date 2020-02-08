package com.spark.anubhav.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.QMatch;

public class PhotoFilter implements Filter {
    private Boolean hasPhoto;

    public PhotoFilter(Boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public BooleanExpression buildPredicate() {
        return hasPhoto ? QMatch.match.mainPhoto.isNotNull() : QMatch.match.mainPhoto.isNull();
    }
}
