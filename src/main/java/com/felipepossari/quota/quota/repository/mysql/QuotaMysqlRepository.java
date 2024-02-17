package com.felipepossari.quota.quota.repository.mysql;

import com.felipepossari.quota.quota.Quota;
import com.felipepossari.quota.quota.QuotaBuilder;
import com.felipepossari.quota.quota.repository.QuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("QuotaMysqlRepository")
@RequiredArgsConstructor
public class QuotaMysqlRepository implements QuotaRepository {

    private final QuotaJpaRepository repository;
    private final QuotaBuilder builder;

    @Override
    public Optional<Quota> findById(String rateKey) {
        var indexOpt = repository.findById(rateKey);
        return indexOpt.map(builder::toQuota);
    }

    @Override
    public Quota save(Quota quota) {
        var entity = builder.toEntity(quota);
        var quotaUpdated = repository.save(entity);
        return builder.toQuota(quotaUpdated);
    }

    @Override
    public Page<Quota> getAll(Pageable pageable) {
        var quotaPage = repository.findAll(pageable);
        return quotaPage.map(builder::toQuota);
    }
}
