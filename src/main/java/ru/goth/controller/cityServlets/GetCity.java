package ru.goth.controller.cityServlets;

import ru.goth.config.JsonConvertor;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.domain.dto.CityDto;
import ru.goth.service.CityService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "getCity", value = "/getCity")
public class GetCity extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GetCity.class.getName());
    private final CityService cityService;

//    public GetCity() throws SQLException {
//        this.cityService = new CityService();
//    }

    public GetCity(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("json/html");
        PrintWriter out = response.getWriter();

        try {
//            List<CityDto> citys = cityService.getAllCities();
//
//            out.println("Cities list:");
//            for (CityDto city : citys) {
//                out.println(city.getName() + " " + city.getDeliveryTime());
//            }
            long id = Long.parseLong(request.getParameter("id"));

            CityDto cityDto = cityService.getCityById(id);

            JsonConvertor<CityDto> jsonConvertor = new JsonConvertor<>();
            jsonConvertor.convertToJson(response, cityDto);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
