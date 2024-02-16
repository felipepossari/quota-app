package com.felipepossari.quota.quota;

import com.felipepossari.quota.quota.repository.mysql.QuotaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuotaService {

    private final QuotaBuilder builder;
    private final QuotaJpaRepository quotaJpaRepository;

    public Quota retrieveQuota(String userId){
        var quotaOpt = quotaJpaRepository.findById(userId);
        if(quotaOpt.isPresent()){
            return builder.toQuota(quotaOpt.get());
        }

        return builder.buildNewQuota(userId, 5);
    }

    public void save(Quota quota){
        quotaJpaRepository.save(builder.toEntity(quota));
    }
}
