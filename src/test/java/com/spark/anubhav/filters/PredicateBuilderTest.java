package com.spark.anubhav.filters;

import com.querydsl.core.types.Predicate;
import com.spark.anubhav.exceptions.UserIdCannotBeNullException;
import com.spark.anubhav.models.CompatibilityRange;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PredicateBuilderTest {

    @Test
    public void shouldBuildPredicateCombiningUserIdAndHasPhoto() {
        UUID userId = UUID.randomUUID();
        Predicate actualPredicate = PredicateBuilder.builder()
                .forUser(userId)
                .hasPhoto(true)
                .build();

        Predicate expectedPredicate = new UserIdFilter(userId).buildPredicate()
                .and(new PhotoFilter(true).buildPredicate());

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldThrowUserIdCannotBeNullExceptionIfUserIdIsProvidedAsNull() {
        assertThrows(UserIdCannotBeNullException.class, () ->
                PredicateBuilder.builder()
                        .forUser(null)
                        .hasPhoto(true)
                        .build());
    }

    @Test
    public void shouldNotAddToPredicateIfHasPhotoIsNull() {
        UUID userId = UUID.randomUUID();
        Predicate actualPredicate = PredicateBuilder.builder()
                .forUser(userId)
                .hasPhoto(null)
                .build();

        Predicate expectedPredicate = new UserIdFilter(userId).buildPredicate();
        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldNotAddToPredicateIfIsFavoriteIsNull() {
        UUID userId = UUID.randomUUID();
        Predicate actualPredicate = PredicateBuilder.builder()
                .forUser(userId)
                .hasPhoto(null)
                .isFavorite(null)
                .build();

        Predicate expectedPredicate = new UserIdFilter(userId).buildPredicate();
        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldBuildPredicateCombiningUserIdAndIsFavorite() {
        UUID userId = UUID.randomUUID();
        Predicate actualPredicate = PredicateBuilder.builder()
                .forUser(userId)
                .isFavorite(true)
                .build();

        Predicate expectedPredicate = new UserIdFilter(userId).buildPredicate()
                .and(new FavouriteFilter(true).buildPredicate());

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldBuildPredicateCombiningUserIdAndCompatibilityScore() {
        UUID userId = UUID.randomUUID();
        CompatibilityRange compatibilityRange = new CompatibilityRange(BigDecimal.valueOf(0.43), BigDecimal.valueOf(0.78));
        Predicate actualPredicate = PredicateBuilder.builder()
                .forUser(userId)
                .withCompatibility(compatibilityRange)
                .build();

        Predicate expectedPredicate = new UserIdFilter(userId).buildPredicate()
                .and(new CompatibilityScoreFilter(compatibilityRange).buildPredicate());

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }


}
