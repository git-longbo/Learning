package com.jxufe.demo.es;

import com.jxufe.demo.entity.es.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESMyUserRepository extends ElasticsearchRepository<User, Long> {
}
