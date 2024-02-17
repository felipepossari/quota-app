package com.felipepossari.quota.quota;

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

    public Quota retrieveQuota(String rateKey) {
        var quotaOpt = repositoryFactory.getRepository().findById(rateKey);
        return quotaOpt.orElseGet(() -> builder.buildNewQuota(rateKey, 5));
    }

    public void update(Quota quota) {
        repositoryFactory.getRepository().save(quota);
    }

    public Page<Quota> getUsersQuota(int page, int pageSize) {
        return repositoryFactory.getRepository().getAll(PageRequest.of(page, pageSize));
    }

    public void syncQuotaCreated(Quota quota) {
        var quotaOpt = repositoryFactory.getIdleRepository().findById(quota.getRateKey());
        if (quotaOpt.isPresent()) {
            log.info("Quota already registered. Skipping sync for quota created. RateKey: {}", quota.getRateKey());
        } else {
            repositoryFactory.getIdleRepository().save(quota);
        }
    }

    public void syncQuotaUpdated(Quota quota) {
        var quotaOpt = repositoryFactory.getIdleRepository().findById(quota.getRateKey());
        if (quotaOpt.isPresent()) {
            if (quota.getUpdatedAt().isBefore(quotaOpt.get().getUpdatedAt())) {
                log.info("UpdatedAt time from registered quota is after the event. " +
                        "Skipping sync for quota updated. RateKey: {}", quota.getRateKey());
            } else {
                repositoryFactory.getIdleRepository().save(quota);
            }
        } else {
            log.info("Quota not registered. Creating quota from quota updated event. RateKey: {}", quota.getRateKey());
            repositoryFactory.getIdleRepository().save(quota);
        }
    }
}
