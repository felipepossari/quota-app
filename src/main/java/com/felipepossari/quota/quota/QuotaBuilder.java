package com.felipepossari.quota.quota;

import com.felipepossari.quota.common.DateTimeUtil;
import com.felipepossari.quota.quota.api.model.QuotaResponse;
import com.felipepossari.quota.quota.repository.elasticsearch.model.QuotaIndex;
import com.felipepossari.quota.quota.repository.mysql.model.QuotaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.quota.api.model.QuotaResponseStatus.AVAILABLE;
import static com.felipepossari.quota.quota.api.model.QuotaResponseStatus.EXCEEDED;

@Component
@RequiredArgsConstructor
public class QuotaBuilder {

    private final DateTimeUtil dateTimeUtil;

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

    public Quota buildNewQuota(String rateKey, Integer quotaLimit) {
        return Quota.builder()
                .rateKey(rateKey)
                .quotaLimit(quotaLimit)
                .remaining(quotaLimit)
                .createdAt(dateTimeUtil.nowUtc())
                .updatedAt(dateTimeUtil.nowUtc())
                .resetTime(dateTimeUtil.nowUtc().plusYears(10))
                .build();
    }

    public QuotaResponse toResponse(Quota quota) {
        return QuotaResponse.builder()
                .quotaLimit(quota.getQuotaLimit())
                .resetTime(quota.getResetTime())
                .rateKey(quota.getRateKey())
                .remaining(quota.getRemaining())
                .status(quota.hasQuotaAvailable() ? AVAILABLE : EXCEEDED)
                .build();
    }
}
