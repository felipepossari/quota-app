package com.felipepossari.quota.quota.repository.elasticsearch;

import com.felipepossari.quota.quota.Quota;
import com.felipepossari.quota.quota.QuotaBuilder;
import com.felipepossari.quota.quota.repository.QuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.data.elasticsearch.core.RefreshPolicy.IMMEDIATE;

@Component("QuotaEsRepository")
@RequiredArgsConstructor
public class QuotaEsRepository implements QuotaRepository {

    private final QuotaElasticsearchRepository repository;
    private final QuotaBuilder builder;

    @Override
    public Optional<Quota> findById(String rateKey) {
        var indexOpt = repository.findById(rateKey);
        return indexOpt.map(builder::toQuota);
    }

    @Override
    public Quota create(Quota quota) {
        var index = builder.toIndex(quota);
        var quotaCreated = repository.save(index, IMMEDIATE);
        return builder.toQuota(quotaCreated);
    }

    @Override
    public Quota update(Quota quota) {
        var index = builder.toIndex(quota);
        var quotaUpdated = repository.save(index, IMMEDIATE);
        return builder.toQuota(quotaUpdated);
    }

    @Override
    public Page<Quota> getAll(Pageable pageable) {
        var quotaPage = repository.findAll(pageable);
        return quotaPage.map(builder::toQuota);
    }
}
