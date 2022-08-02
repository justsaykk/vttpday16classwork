package classwork.weatherapp.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Weather {

    private String name;
    private String icon;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Weather create(JsonObject jo) {
        Weather w = new Weather();
        w.setName(jo.getString("main"));
        w.setDescription(jo.getString("description"));
        w.setIcon(jo.getString("icon"));
        return w;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("main", name)
                .add("icon", icon)
                .add("description", description)
                .build();
    }

}
