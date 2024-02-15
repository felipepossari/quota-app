package com.felipepossari.quota.user.repository.elasticsearch;

import com.felipepossari.quota.user.repository.elasticsearch.model.UserIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserElasticsearchRepository extends ElasticsearchRepository<UserIndex, String> {
}
