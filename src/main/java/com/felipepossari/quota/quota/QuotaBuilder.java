package com.felipepossari.quota.quota;

import com.felipepossari.quota.quota.repository.mysql.model.QuotaEntity;
import org.springframework.stereotype.Component;

@Component
public class QuotaBuilder {

    public Quota toQuota(QuotaEntity entity) {
        return Quota.builder()
                .quotaLimit(entity.getQuotaLimit())
                .resetTime(entity.getResetTime())
                .rateKey(entity.getRateKey())
                .remaining(entity.getRemaining())
                .build();
    }

    public QuotaEntity toEntity(Quota quota) {
        return QuotaEntity.builder()
                .quotaLimit(quota.getQuotaLimit())
                .resetTime(quota.getResetTime())
                .rateKey(quota.getRateKey())
                .remaining(quota.getRemaining())
                .build();
    }

    public Quota buildNewQuota(String rateKey, Integer quotaLimit){
        return Quota.builder()
                .rateKey(rateKey)
                .quotaLimit(quotaLimit)
                .remaining(quotaLimit)
                .build();
    }
}
