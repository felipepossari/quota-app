package com.felipepossari.quota.quota.repository.mysql;

import com.felipepossari.quota.quota.Quota;
import com.felipepossari.quota.quota.QuotaBuilder;
import com.felipepossari.quota.quota.repository.QuotaRepository;
 import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("QuotaMysqlRepository")
@RequiredArgsConstructor
public class QuotaMysqlRepository implements QuotaRepository {

    private final QuotaJpaRepository repository;
    private final QuotaBuilder builder;

    @Override
    public Optional<Quota> findById(String userId) {
        var indexOpt = repository.findById(userId);
        return indexOpt.map(builder::toQuota);
    }

    @Override
    public Quota save(Quota quota) {
        var entity = builder.toEntity(quota);
        var quotaUpdated = repository.save(entity);
        return builder.toQuota(quotaUpdated);
    }
}
