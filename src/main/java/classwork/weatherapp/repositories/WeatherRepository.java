package classwork.weatherapp.repositories;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class WeatherRepository {

    @Autowired
    @Qualifier("redis")
    private RedisTemplate<String, String> redisTemplate;

    @Value("${weather.cache.duration}")
    private Long cacheTime;

    public void save(String payload, String city) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set(city.toLowerCase(), payload, Duration.ofMinutes(cacheTime));
    }

    public Optional<String> get(String city) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        String value = valueOps.get(city.toLowerCase());
        if (null == value) {
            return Optional.empty();
        }
        return Optional.of(value);
    }
}
