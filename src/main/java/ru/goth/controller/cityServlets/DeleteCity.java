package ru.goth.controller.cityServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.service.CityService;
import ru.goth.service.impl.CityServiceImpl;
import ru.goth.repository.impl.CityRepositoryImpl;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "deleteCity", value = "/deleteCity")
public class DeleteCity extends HttpServlet {

    private final CityService cityService;
    private static final Logger logger = Logger.getLogger(DeleteCity.class.getName());

    public DeleteCity() {
        this.cityService = new CityServiceImpl(new CityRepositoryImpl());
    }

    public DeleteCity(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            long id = Long.parseLong(request.getParameter("id"));
            boolean isDeleted = cityService.deleteCity(id);

            if (isDeleted) {
                response.sendRedirect("manageCity.jsp?success=delete");
            } else {
                response.sendRedirect("manageCity.jsp?error=not_found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("manageCity.jsp?error=invalid_id");
        } catch (Exception e) {
            logger.severe("Error deleting city: " + e.getMessage());
            response.sendRedirect("manageCity.jsp?error=server_error");
        }
    }
}
