package ru.goth.controller.mythologyServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.domain.dto.MythologyDto;
import ru.goth.repository.impl.MythologyRepositoryImpl;
import ru.goth.service.MythologyService;
import ru.goth.service.impl.MythologyServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "getMythology", value = "/getMythology")
public class GetMythology extends HttpServlet {

    private static final Logger logger = Logger.getLogger(GetMythology.class.getName());
    private final MythologyService mythologyService;

    public GetMythology() {
        this.mythologyService = new MythologyServiceImpl(new MythologyRepositoryImpl());
    }

    public GetMythology(MythologyService mythologyService) {
        this.mythologyService = mythologyService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String action = request.getParameter("action");
            String idParam = request.getParameter("id");

            if ("all".equals(action)) {
                List<MythologyDto> mythologies = mythologyService.getAllMythologies();
                request.setAttribute("mythologies", mythologies);
                request.getRequestDispatcher("/getMythology.jsp").forward(request, response);
            } else if (idParam != null && !idParam.isEmpty()) {
                long id = Long.parseLong(idParam);
                MythologyDto mythology = mythologyService.getMythologyById(id);

                if (mythology != null) {
                    request.setAttribute("mythologies", List.of(mythology));
                    request.getRequestDispatcher("/getMythology.jsp").forward(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Mythology not found\"}");
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
