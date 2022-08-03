# Relationships between elements of MVC

If given a HTML element that looks like this: </br>
```html
<form method="GET" action="cities">
    <input type="text" name="city">
</form>
``` 
</br>
The above HTML code would produce a HTTP request that looks like this: </br>
<code>GET /cities?city=<value></code>
</br>

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


