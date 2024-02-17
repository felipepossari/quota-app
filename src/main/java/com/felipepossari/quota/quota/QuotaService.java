package com.felipepossari.quota.quota;

import com.felipepossari.quota.common.config.QuotaProperties;
import com.felipepossari.quota.quota.repository.QuotaRepositoryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuotaService {

    private final QuotaBuilder builder;
    private final QuotaRepositoryFactory repositoryFactory;
    private final QuotaProducer quotaProducer;
    private final QuotaProperties quotaProperties;

    public Quota retrieveQuota(String rateKey) {
        var quotaOpt = repositoryFactory.getRepository().findById(rateKey);
        if (quotaOpt.isPresent()) {
            return quotaOpt.get();
        }
        Quota quota = repositoryFactory.getRepository().create(builder.buildNewQuota(rateKey, quotaProperties.getLimit()));
        quotaProducer.sendQuotaCreatedMessage(quota);
        return quota;
    }

    public void update(Quota quota) {
        var quotaUpdated = repositoryFactory.getRepository().update(quota);
        quotaProducer.sendQuotaUpdatedMessage(quotaUpdated);
    }

    public Page<Quota> getUsersQuota(int page, int pageSize) {
        return repositoryFactory.getRepository().getAll(PageRequest.of(page, pageSize));
    }

    public void syncQuotaCreated(Quota quota) {
        var quotaOpt = repositoryFactory.getIdleRepository().findById(quota.getRateKey());
        if (quotaOpt.isPresent()) {
            log.info("Quota already registered. Skipping sync for quota created. RateKey: {}", quota.getRateKey());
        } else {
            repositoryFactory.getIdleRepository().create(quota);
        }
    }

    public void syncQuotaUpdated(Quota quota) {
        var quotaOpt = repositoryFactory.getIdleRepository().findById(quota.getRateKey());
        if (quotaOpt.isPresent()) {
            if (quota.getUpdatedAt().isBefore(quotaOpt.get().getUpdatedAt())) {
                log.info("UpdatedAt time from registered quota is after the event. " +
                        "Skipping sync for quota updated. RateKey: {}", quota.getRateKey());
            } else {
                repositoryFactory.getIdleRepository().update(quota);
            }
        } else {
            log.info("Quota not registered. Creating quota from quota updated event. RateKey: {}", quota.getRateKey());
            repositoryFactory.getIdleRepository().create(quota);
        }
    }
}
