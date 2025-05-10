package ru.goth.deliveryTimeCalculator;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class DeliveryTimeCalculator {
    public static Long getMinutes(String cityName) {

        try {
            String lat = Double.toString(DGISGeocoder.getCoordinates(cityName, "1ce1bb8a-5231-4ad6-a336-0aa753c5e892")[0]);
            String lon = Double.toString(DGISGeocoder.getCoordinates(cityName, "1ce1bb8a-5231-4ad6-a336-0aa753c5e892")[1]);

            String point1 = "30.404074,60.004942";
            String point2 = lat + "," + lon;

            String osrmUrl = "http://router.project-osrm.org/route/v1/driving/"
                    + point1 + ";" + point2
                    + "?overview=false&alternatives=false";

            URL url = new URL(osrmUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                String response = new BufferedReader(new InputStreamReader(conn.getInputStream()))
                        .lines().collect(Collectors.joining("\n"));

                JSONObject json = new JSONObject(response);
                double duration = json.getJSONArray("routes")
                        .getJSONObject(0)
                        .getDouble("duration");

                Long minutes = (Long) Math.round(duration / 60);
                return minutes;
            } else {
                System.err.println("Ошибка API: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
        return null;
    }
}
