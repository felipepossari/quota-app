package com.felipepossari.quota.unit.quota;

import com.felipepossari.quota.unit.base.QuotaTestBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuotaTest {

    @Test
    void consumeQuotaShouldConsumeQuotaSuccessfully() {
        // given
        var quota = QuotaTestBuilder.aQuota().remaining(5).build();
        var updatedAt = LocalDateTime.now(ZoneOffset.UTC);

        // when
        quota.consumeQuota(2, updatedAt);

        // then
        assertEquals(3, quota.getRemaining());
        assertEquals(updatedAt, quota.getUpdatedAt());
    }

    @Test
    void consumeQuotaShouldConsumeQuotaThreeTimesSuccessfully() {
        // given
        int quotaRemaining = 3;
        var quota = QuotaTestBuilder.aQuota().remaining(quotaRemaining).build();

        // when
        for (int i = 0; i < quotaRemaining; i++) {
            quota.consumeQuota(1, LocalDateTime.now(ZoneOffset.UTC));
        }
        // then
        assertFalse(quota.isQuotaReached());

        // consume one more time and check if quota is over
        quota.consumeQuota(1, LocalDateTime.now(ZoneOffset.UTC));
        assertTrue(quota.isQuotaReached());
    }

    @Test
    void isQuotaReachedShouldReturnTrueWhenThereIsNoRemainingQuota() {
        // given
        var quota = QuotaTestBuilder.aQuota().remaining(-1).build();

        // when
        var actualResult = quota.isQuotaReached();

        // then
        assertTrue(actualResult);
    }

    @Test
    void isQuotaReachedShouldReturnFalseWhenLastRemainingQuotaIsReached() {
        // given
        var quota = QuotaTestBuilder.aQuota().remaining(0).build();

        // when
        var actualResult = quota.isQuotaReached();

        // then
        assertFalse(actualResult);
    }

    @Test
    void hasQuotaAvailableShouldReturnTrueWhenThereIsRemainingQuota() {
        // given
        var quota = QuotaTestBuilder.aQuota().remaining(1).build();

        // when
        var actualResult = quota.hasQuotaAvailable();

        // then
        assertTrue(actualResult);
    }

    @Test
    void hasQuotaAvailableShouldReturnFalseWhenThereIsNoRemainingQuota() {
        // given
        var quota = QuotaTestBuilder.aQuota().remaining(0).build();

        // when
        var actualResult = quota.hasQuotaAvailable();

        // then
        assertFalse(actualResult);
    }
}
