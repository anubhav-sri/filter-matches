package com.spark.anubhav.filters;


import com.querydsl.core.types.dsl.BooleanExpression;

public interface Filter {
    BooleanExpression buildPredicate();
}
