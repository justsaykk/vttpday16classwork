# Relationships between elements of MVC

If given a HTML element that looks like this: </br>
```html
<form method="GET" action="cities">
    <input type="text" name="city">
</form>
``` 
</br>
The above HTML code would produce a HTTP request that looks like this: </br>

```xml
GET /cities?city=berlin
content-type: application/x-www-form-urlencoded
```
> The content type is present in the request because we're using a form to query.</br>

And the required controller to handle such a request would look like this: </br>

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
Step 1: You'll need the base url</br>
<code>https://api.openweathermap.org/data/2.5/weather</code></br>
Step 2: Build the url by adding the query parameters: </br>

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



