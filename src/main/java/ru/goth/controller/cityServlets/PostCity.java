package ru.goth.controller.cityServlets;

import com.google.gson.Gson;
import ru.goth.config.JsonConvertor;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.domain.dto.CityDto;
import ru.goth.service.CityService;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(name = "getCity", value = "/getCity")
public class PostCity extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GetCity.class.getName());
    private static final Gson gson = new Gson();
    private final CityService cityService;

//    public GetCity() throws SQLException {
//        this.cityService = new CityService();
//    }

    public PostCity(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //response.setContentType("text/html");

        try {
            BufferedReader reader = request.getReader();
            CityDto newCity = gson.fromJson(reader, CityDto.class);
            if (newCity.getName() == null || newCity.getDeliveryTime() == null) {
                response.sendError(400, "Field 'name' and 'delivery_time' are necessary");
                return;
            }
            cityService.createCity(newCity);
            response.setStatus(201);
            response.getWriter().write("{\"status\": \"City added\"}");

        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"error\": \"Server error: " + e.getMessage() + "\"}");
            e.printStackTrace(); // Логируем в консоль
        }
    }
}
