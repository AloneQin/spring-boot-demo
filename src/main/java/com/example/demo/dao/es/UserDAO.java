package com.example.demo.dao.es;

import com.example.demo.model.pojo.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "spring.data.elasticsearch.enabled", havingValue = "true")
public interface UserDAO extends ElasticsearchRepository<User, Integer> {
}
