package ru.goth.controller.cityServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.domain.dto.CityDto;
import ru.goth.service.CityService;
import ru.goth.service.impl.CityServiceImpl;
import ru.goth.repository.impl.CityRepositoryImpl;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "getCity", value = "/getCity")
public class GetCity extends HttpServlet {

    private static final Logger logger = Logger.getLogger(GetCity.class.getName());
    private final CityService cityService;

    public GetCity() {
        this.cityService = new CityServiceImpl(new CityRepositoryImpl());
    }

    public GetCity(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        try {
            String action = request.getParameter("action");
            String idParam = request.getParameter("id");

            if ("all".equals(action)) {
                List<CityDto> cities = cityService.getAllCities();
                request.setAttribute("cities", cities);
                request.getRequestDispatcher("/getCity.jsp").forward(request, response);
            } else if (idParam != null && !idParam.isEmpty()) {
                long id = Long.parseLong(idParam);
                CityDto city = cityService.getCityById(id);

                if (city != null) {
                    request.setAttribute("cities", List.of(city));
                    request.getRequestDispatcher("/getCity.jsp").forward(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"City not found\"}");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Missing parameters\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid ID format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Server error\"}");
            e.printStackTrace();
        }
    }
}
