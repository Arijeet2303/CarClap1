package com.mycompany.carclap.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationTracker {

    // The user's latitude and longitude
    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("lon")
    private double longitude;

    // The OSM API URL
    private String osmApiUrl = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=%s&lon=%s";

    public LocationTracker(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Schedule the location tracking every 5 seconds
    public void trackLocation() {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(osmApiUrl, latitude, longitude);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        // Do something with the location data here
        System.out.println("Location: " + response.get("display_name"));
    }

}

