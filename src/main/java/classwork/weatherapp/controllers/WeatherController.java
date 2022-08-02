package classwork.weatherapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import classwork.weatherapp.models.Weather;
import classwork.weatherapp.services.WeatherService;

/*
 * API: 
 * https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
 */

@Controller
@RequestMapping(path = "/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherSvc;

    @GetMapping
    public String getWeather(Model model, @RequestParam String city) {
        List<Weather> result = weatherSvc.getWeather(city);
        model.addAttribute("city", city);
        model.addAttribute("result", result);
        return "weather";
    }
}
