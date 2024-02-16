package com.felipepossari.quota.quota.repository.mysql;

import com.felipepossari.quota.quota.repository.mysql.model.QuotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotaJpaRepository extends JpaRepository<QuotaEntity, String> {
}
