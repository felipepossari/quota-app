package com.felipepossari.quota.unit.base;

import com.felipepossari.quota.quota.Quota;

import java.time.LocalDateTime;

import static com.felipepossari.quota.unit.base.Constants.CREATED_AT;
import static com.felipepossari.quota.unit.base.Constants.QUOTA_LIMIT;
import static com.felipepossari.quota.unit.base.Constants.RATE_KEY;
import static com.felipepossari.quota.unit.base.Constants.REMAINING_LIMIT;
import static com.felipepossari.quota.unit.base.Constants.RESET_TIME;
import static com.felipepossari.quota.unit.base.Constants.UPDATED_AT;

public class QuotaTestBuilder {
    private String rateKey = RATE_KEY;
    private Integer quotaLimit = QUOTA_LIMIT;
    private Integer remaining = REMAINING_LIMIT;
    private LocalDateTime resetTime = RESET_TIME;
    private LocalDateTime createdAt = CREATED_AT;
    private LocalDateTime updatedAt = UPDATED_AT;

    private QuotaTestBuilder(){}

    public static QuotaTestBuilder aQuota(){
        return new QuotaTestBuilder();
    }

    public QuotaTestBuilder rateKey(String rateKey){
        this.rateKey = rateKey;
        return this;
    }

    public QuotaTestBuilder quotaLimit(Integer quotaLimit){
        this.quotaLimit = quotaLimit;
        return this;
    }

    public QuotaTestBuilder remaining(Integer remaining){
        this.remaining = remaining;
        return this;
    }

    public QuotaTestBuilder resetTime(LocalDateTime resetTime){
        this.resetTime = resetTime;
        return this;
    }

    public QuotaTestBuilder updatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
        return this;
    }

    public QuotaTestBuilder createdAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
        return this;
    }

    public Quota build(){
        return Quota.builder()
                .updatedAt(updatedAt)
                .createdAt(createdAt)
                .resetTime(resetTime)
                .remaining(remaining)
                .quotaLimit(quotaLimit)
                .rateKey(rateKey)
                .build();
    }
}
