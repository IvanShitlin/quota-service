package io.vicarius.quotaservice.repository;

import io.vicarius.quotaservice.domain.document.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserElasticSearchRepository extends ElasticsearchRepository<UserDocument, String> {
}
