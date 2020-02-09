package com.spark.anubhav.filters;

import com.querydsl.core.types.Predicate;
import com.spark.anubhav.exceptions.UserIdCannotBeNullException;
import com.spark.anubhav.models.AgeRange;
import com.spark.anubhav.models.CompatibilityRange;
import com.spark.anubhav.models.HeightRange;
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

    @Test
    public void shouldNotAddToPredicateIfCompatibilityRangeIsNull() {
        UUID userId = UUID.randomUUID();
        Predicate actualPredicate = PredicateBuilder.builder()
                .forUser(userId)
                .withCompatibility(null)
                .build();

        Predicate expectedPredicate = new UserIdFilter(userId).buildPredicate();
        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldNotAddToPredicateIfAgeRangeIsNull() {
        UUID userId = UUID.randomUUID();
        Predicate actualPredicate = PredicateBuilder.builder()
                .forUser(userId)
                .withAgeBetween(null)
                .build();

        Predicate expectedPredicate = new UserIdFilter(userId).buildPredicate();
        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldBuildPredicateCombiningUserIdAndAgeLimits() {
        UUID userId = UUID.randomUUID();
        AgeRange ageRange = new AgeRange(18, 35);
        Predicate actualPredicate = PredicateBuilder.builder()
                .forUser(userId)
                .withAgeBetween(ageRange)
                .build();

        Predicate expectedPredicate = new UserIdFilter(userId).buildPredicate()
                .and(new AgeFilter(ageRange).buildPredicate());

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldBuildPredicateCombiningUserIdAndHeightLimit() {
        UUID userId = UUID.randomUUID();
        HeightRange heightRange = new HeightRange(178, 210);
        Predicate actualPredicate = PredicateBuilder.builder()
                .forUser(userId)
                .withHeightBetween(heightRange)
                .build();

        Predicate expectedPredicate = new UserIdFilter(userId).buildPredicate()
                .and(new HeightFilter(heightRange).buildPredicate());

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldNotAddToHeioghtPredicateIfHeightRangeIsNull() {
        UUID userId = UUID.randomUUID();
        Predicate actualPredicate = PredicateBuilder.builder()
                .forUser(userId)
                .withHeightBetween(null)
                .build();

        Predicate expectedPredicate = new UserIdFilter(userId).buildPredicate();
        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldBuildPredicateCombiningUserIdAndContactsExchanged() {
        UUID userId = UUID.randomUUID();
        Predicate actualPredicate = PredicateBuilder.builder()
                .forUser(userId)
                .isAlreadyAContact(true)
                .build();

        Predicate expectedPredicate = new UserIdFilter(userId).buildPredicate()
                .and(new InContactFilter(true).buildPredicate());

        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }

    @Test
    public void shouldNotAddInContactPredicateIfIsInContactIsNull() {
        UUID userId = UUID.randomUUID();
        Predicate actualPredicate = PredicateBuilder.builder()
                .forUser(userId)
                .isAlreadyAContact(null)
                .build();

        Predicate expectedPredicate = new UserIdFilter(userId).buildPredicate();
        assertThat(actualPredicate).isEqualTo(expectedPredicate);
    }
}
