package com.felipepossari.quota.quota.repository.elasticsearch;

import com.felipepossari.quota.quota.Quota;
import com.felipepossari.quota.quota.QuotaBuilder;
import com.felipepossari.quota.quota.repository.QuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.data.elasticsearch.core.RefreshPolicy.IMMEDIATE;

@Component("QuotaEsRepository")
@RequiredArgsConstructor
public class QuotaEsRepository implements QuotaRepository {

    private final QuotaElasticsearchRepository repository;
    private final QuotaBuilder builder;

    @Override
    public Optional<Quota> findById(String userId) {
        var indexOpt = repository.findById(userId);
        return indexOpt.map(builder::toQuota);
    }

    @Override
    public Quota save(Quota quota) {
        var index = builder.toIndex(quota);
        var quotaUpdated = repository.save(index, IMMEDIATE);
        return builder.toQuota(quotaUpdated);
    }
}
