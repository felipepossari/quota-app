package com.felipepossari.quota.quota.repository.elasticsearch;

import com.felipepossari.quota.quota.repository.elasticsearch.model.QuotaIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QuotaElasticsearchRepository extends ElasticsearchRepository<QuotaIndex, String> {
}
