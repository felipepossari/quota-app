package com.felipepossari.quota.quota.repository;

import com.felipepossari.quota.quota.Quota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuotaRepository {

    Optional<Quota> findById(String rateKey);

    Quota save(Quota quota);

    Page<Quota> getAll(Pageable pageable);
}
