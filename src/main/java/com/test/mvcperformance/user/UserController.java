package com.test.mvcperformance.user;

import com.test.mvcperformance.redis.RedisRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    private final RedisRepository redisRepository;
    private RestTemplate restTemplate=new RestTemplate();

    public UserController(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User find() {
        User user = redisRepository.find();
        user.setHobby(restTemplate.getForEntity("http://172.16.0.25:8046/test/api",String.class)
                .getBody());
        return user;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerUser(@RequestBody User user) {

        redisRepository.save(user);
    }
}
