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

@WebServlet(name = "updateCity", value = "/updateCity")
public class UpdateCity extends HttpServlet {

    private final CityService cityService;

    public UpdateCity() {
        this.cityService = new CityServiceImpl(new CityRepositoryImpl());
    }

    public UpdateCity(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            long id = Long.parseLong(request.getParameter("id"));
            String name = request.getParameter("name");
            long deliveryTime = Long.parseLong(request.getParameter("deliveryTime"));

            if (name == null) {
                response.sendRedirect("updateCity.jsp?error=Server+error");
            }

            CityDto cityDto = new CityDto(name, deliveryTime);
            cityDto.setId(id);

            CityDto updatedCity = cityService.updateCity(id, cityDto);

            if (updatedCity != null) {
                response.sendRedirect("manageCity.jsp?success=City+updated");
            } else {
                response.sendRedirect("updateCity.jsp?error=City+not+found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("updateCity.jsp?error=Invalid+ID+or+delivery+time");
        } catch (Exception e) {
            response.sendRedirect("updateCity.jsp?error=Server+error");
        }
    }
}
