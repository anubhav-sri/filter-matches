package com.spark.anubhav.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spark.anubhav.models.QMatch;

import javax.annotation.Nullable;

public class PhotoFilter implements Filter {
    private Boolean hasPhoto;

    public PhotoFilter(@Nullable Boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public BooleanExpression buildPredicate() {
        if (hasPhoto == null) return null;
        if (hasPhoto) return QMatch.match.mainPhoto.isNotNull();
        return QMatch.match.mainPhoto.isNull();
    }
}
