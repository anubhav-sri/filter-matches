package com.spark.anubhav.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatch is a Querydsl query type for Match
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMatch extends EntityPathBase<Match> {

    private static final long serialVersionUID = -586844342L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatch match = new QMatch("match");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final QCity city;

    public final NumberPath<java.math.BigDecimal> compatibilityScore = createNumber("compatibilityScore", java.math.BigDecimal.class);

    public final StringPath displayName = createString("displayName");

    public final BooleanPath favourite = createBoolean("favourite");

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath jobTitle = createString("jobTitle");

    public final SimplePath<java.net.URL> mainPhoto = createSimple("mainPhoto", java.net.URL.class);

    public final NumberPath<Integer> numberOfContactsExchanged = createNumber("numberOfContactsExchanged", Integer.class);

    public final StringPath religion = createString("religion");

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QMatch(String variable) {
        this(Match.class, forVariable(variable), INITS);
    }

    public QMatch(Path<? extends Match> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatch(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatch(PathMetadata metadata, PathInits inits) {
        this(Match.class, metadata, inits);
    }

    public QMatch(Class<? extends Match> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.city = inits.isInitialized("city") ? new QCity(forProperty("city")) : null;
    }

}

