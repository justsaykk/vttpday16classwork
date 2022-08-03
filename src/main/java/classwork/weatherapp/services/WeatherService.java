package classwork.weatherapp.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import classwork.weatherapp.models.Weather;
import classwork.weatherapp.repositories.WeatherRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WeatherService {

    private static final String api = "https://api.openweathermap.org/data/2.5/weather";
    // ?q={city name}&appid={API key}

    @Value("${API_KEY}") // use CLI export to set the API key
    private String apiKey;

    @Autowired
    private WeatherRepository weatherRepo;

    public List<Weather> getWeather(String city) {

        // Check if weather is cached
        String payload;
        ResponseEntity<String> res;
        Optional<String> opt = weatherRepo.get(city);

        if (opt.isEmpty()) {

            System.out.println("Getting weather from API");

            // Crafting the URL to fetch
            String url = UriComponentsBuilder.fromUriString(api)
                    .queryParam("q", city)
                    .queryParam("appid", apiKey)
                    .toUriString();

            // Create the GET request (GET URL)
            RequestEntity<Void> req = RequestEntity.get(url).build();

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
        } else {
            // Retrieve the value of the box
            payload = opt.get();
        }

        // Convert to String to Reader
        Reader in = new StringReader(payload);

        // Create JSON reader using reader
        JsonReader jsonReader = Json.createReader(in);

        // Create JSON object
        JsonObject weatherResult = jsonReader.readObject();
        JsonArray cities = weatherResult.getJsonArray("weather");

        List<Weather> list = new LinkedList<>();

        for (int i = 0; i < cities.size(); i++) {
            JsonObject jo = cities.getJsonObject(i);
            list.add(Weather.create(jo));
        }
        return list;
    }
}
