package com.felipepossari.quota.quota;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Quota {
    private String rateKey;
    private Integer quotaLimit;
    private Integer remaining;
    private LocalDateTime resetTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void consumeQuota(int quantity) {
        remaining = remaining - quantity;
        updatedAt = LocalDateTime.now();
    }

    public boolean isQuotaReached() {
        return remaining < 0;
    }

    public long getResetTimeInSeconds(LocalDateTime now){
        return resetTime.toEpochSecond(ZoneOffset.UTC) - now.toEpochSecond(ZoneOffset.UTC);
    }
}
