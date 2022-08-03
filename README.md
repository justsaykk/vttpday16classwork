# Relationships between elements of MVC

If given a HTML element that looks like this:

```html
<form method="GET" action="cities">
    <input type="text" name="city">
</form>
``` 

The above HTML code would produce a HTTP request that looks like this:

```xml
GET /cities?city=berlin
content-type: application/x-www-form-urlencoded
```

> The content type is present in the request because we're using a form to query.

And the required controller to handle such a request would look like this:

```java
@Controller
@RequestMapping(path = "/cities")
public class WeatherController {
    
    @GetMapping
    public Void getWeather(@RequestParam String city) {
        // code here...
    }
}
```
# How to "fetch" from an API?
Step 1: You'll need the base url

<code>https://api.openweathermap.org/data/2.5/weather</code>

Step 2: Build the url by adding the query parameters:

```java
String payload;
ResponseEntity<String> res;

// Crafting the URL to fetch
String url = UriComponentsBuilder.fromUriString(api)
        .queryParam("q", city)
        .queryParam("appid", apiKey)
        .toUriString();

// Make the api call
RestTemplate template = new RestTemplate();
try {
    res = template.exchange(req, String.class);
} catch (Exception e) {
    System.err.println(e);
    return Collections.emptyList();
}

// Get the payload
payload = res.getBody();
weatherRepo.save(payload, city);
```
# Notes on @Autowired and @Bean

`@Autowire` will look for distinct `@Bean`.

Therefore, lets say we have such a configuration file:

```java
@Configuration
public class AppConfig {
    
    @Bean
    RedisTemplate<Long, String> config1() {
        // code
    }

    @Bean
    RedisTemplate<String, String> config2() {
        // code
    }

    @Bean
    RedisTemplate<Long, String> config3() {
        // code
    }
}
```

And we want to call `config2`, we'll need such a code:

```java
@Autowire
private RedisTemplate<String, String> redisConfig2;
```

However, we will run into problems if we want to call `config1` as it shares the same signatures as `config3`.

Therefore there needs to be another layer of annotation:

```java
@Configuration
public class AppConfig {
    
    @Bean("config1")
    RedisTemplate<Long, String> config1() {
        // code
    }

    @Bean
    RedisTemplate<String, String> config2() {
        // code
    }

    @Bean
    RedisTemplate<Long, String> config3() {
        // code
    }
}
```

And to call that specific `@Bean`, we would need the `@Qualifier` annotation:

```java
@Autowire
@Qualifier("config1")
private RedisTemplate<String, String> redisConfig1;
```



