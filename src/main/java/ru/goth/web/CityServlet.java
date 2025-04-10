package ru.goth.web;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.domain.dto.CityDto;
import ru.goth.repository.impl.CityRepositoryImpl;
import ru.goth.service.CityService;
import ru.goth.service.impl.CityServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.List;

@WebServlet("/city")
public class CityServlet extends HttpServlet {
    private static final Gson gson = new Gson();
    private CityService cityService;

    @Override
    public void init() {
        cityService = new CityServiceImpl(new CityRepositoryImpl());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        List<CityDto> citys = cityService.getAllCities();

        out.println("Список городов:");
        for (CityDto city : citys) {
            out.println(city.getName() + " " + city.getDeliveryTime());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();
            CityDto newCity = gson.fromJson(reader, CityDto.class);
            if (newCity.getName() == null || newCity.getDeliveryTime() == null) {
                response.sendError(400, "Поля 'name' и 'delivery_time' обязательны");
                return;
            }
            cityService.createCity(newCity);
            response.setStatus(201);
            response.getWriter().write("{\"status\": \"Город добавлен\"}");

        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"error\": \"Ошибка сервера: " + e.getMessage() + "\"}");
            e.printStackTrace(); // Логируем в консоль
        }
    }
}
