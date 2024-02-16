package com.felipepossari.quota.quota;

import com.felipepossari.quota.quota.repository.QuotaRepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuotaService {

    private final QuotaBuilder builder;
    private final QuotaRepositoryFactory repositoryFactory;

    public Quota retrieveQuota(String userId) {
        var quotaOpt = repositoryFactory.getRepository().findById(userId);
        return quotaOpt.orElseGet(() -> builder.buildNewQuota(userId, 5));
    }

    public void save(Quota quota) {
        repositoryFactory.getRepository().save(quota);
    }
}
