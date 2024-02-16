package com.felipepossari.quota.quota;

import com.felipepossari.quota.quota.repository.elasticsearch.model.QuotaIndex;
import com.felipepossari.quota.quota.repository.mysql.model.QuotaEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class QuotaBuilder {

    public Quota toQuota(QuotaEntity entity) {
        return Quota.builder()
                .quotaLimit(entity.getQuotaLimit())
                .resetTime(entity.getResetTime())
                .rateKey(entity.getRateKey())
                .remaining(entity.getRemaining())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public Quota toQuota(QuotaIndex entity) {
        return Quota.builder()
                .quotaLimit(entity.getQuotaLimit())
                .resetTime(entity.getResetTime())
                .rateKey(entity.getRateKey())
                .remaining(entity.getRemaining())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public QuotaEntity toEntity(Quota quota) {
        return QuotaEntity.builder()
                .quotaLimit(quota.getQuotaLimit())
                .resetTime(quota.getResetTime())
                .rateKey(quota.getRateKey())
                .remaining(quota.getRemaining())
                .createdAt(quota.getCreatedAt())
                .updatedAt(quota.getUpdatedAt())
                .build();
    }

    public QuotaIndex toIndex(Quota quota) {
        return QuotaIndex.builder()
                .quotaLimit(quota.getQuotaLimit())
                .resetTime(quota.getResetTime())
                .rateKey(quota.getRateKey())
                .remaining(quota.getRemaining())
                .createdAt(quota.getCreatedAt())
                .updatedAt(quota.getUpdatedAt())
                .build();
    }

    public Quota buildNewQuota(String rateKey, Integer quotaLimit){
        return Quota.builder()
                .rateKey(rateKey)
                .quotaLimit(quotaLimit)
                .remaining(quotaLimit)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .resetTime(LocalDateTime.now().plusYears(10))
                .build();
    }
}
