package ru.goth.deliveryTimeCalculator;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.stream.Collectors;

public class DGISGeocoder {
    public static double[] getCoordinates(String address, String apiKey) throws Exception {

        Locale.setDefault(Locale.US);
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String url = "https://catalog.api.2gis.com/3.0/items/geocode?q="
                + encodedAddress + "&fields=items.point&key=" + apiKey;

        String response = sendHttpGetRequest(url);
        JSONObject json = new JSONObject(response);

        if (!json.has("result")) {
            throw new RuntimeException("Неверный формат ответа API");
        }

        JSONObject result = json.getJSONObject("result");
        if (!result.has("items") || result.getJSONArray("items").isEmpty()) {
            throw new RuntimeException("Адрес не найден");
        }

        JSONObject firstItem = result.getJSONArray("items").getJSONObject(0);
        JSONObject point = firstItem.getJSONObject("point");
        return new double[]{point.getDouble("lon"), point.getDouble("lat")};
    }


    static String sendHttpGetRequest(String url) throws Exception {

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            return reader.lines().collect(Collectors.joining());
        }
    }
}