package ru.goth.controller.cityServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.domain.dto.CityDto;
import ru.goth.repository.impl.CityRepositoryImpl;
import ru.goth.service.CityService;
import ru.goth.service.impl.CityServiceImpl;

import java.io.IOException;

@WebServlet(name = "postCity", value = "/postCity")
public class PostCity extends HttpServlet {

    private final CityService cityService;

    public PostCity() {
        this.cityService = new CityServiceImpl(new CityRepositoryImpl());
    }

    public PostCity(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            String name = request.getParameter("name");
            long deliveryTime = Long.parseLong(request.getParameter("deliveryTime"));

            if (name == null || name.isEmpty()) {
                response.sendRedirect("postCity.jsp?error=true");
                return;
            }

            CityDto cityDto = new CityDto(name, deliveryTime);
            cityService.createCity(cityDto);

            response.sendRedirect("manageCity.jsp?success=true");
        } catch (Exception e) {
            response.sendRedirect("postCity.jsp?error=true");
        }
    }
}
