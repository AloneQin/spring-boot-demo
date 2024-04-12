package com.example.demo.dao.es;

import com.example.demo.model.pojo.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends ElasticsearchRepository<User, Integer> {
}
