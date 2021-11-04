package com.test.mvcperformance.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.mvcperformance.user.User;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class RedisRepository {
    private final ValueOperations<String, String> valueOperations;
    private final ObjectMapper objectMapper;
    private final String REDIS_KEY="performData";

    public RedisRepository(RedisTemplate<String,String> redisTemplate, ObjectMapper objectMapper){
        valueOperations = redisTemplate.opsForValue();
        this.objectMapper = objectMapper;
    }

    public User find() {
        return getUser(valueOperations.get(REDIS_KEY));
    }

    private User getUser(String jsonStr){
        try {
            return objectMapper.readValue(jsonStr,User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(User user){
        valueOperations.set(REDIS_KEY,getJsonStr(user));
    }

    private String getJsonStr(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
