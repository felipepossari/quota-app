package com.felipepossari.quota.quota.repository;

import com.felipepossari.quota.quota.Quota;

import java.util.Optional;

public interface QuotaRepository {

    Optional<Quota> findById(String userId);

    Quota save(Quota quota);
}
