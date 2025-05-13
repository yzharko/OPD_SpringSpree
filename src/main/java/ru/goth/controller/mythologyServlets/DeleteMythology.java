package ru.goth.controller.mythologyServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.repository.impl.MythologyRepositoryImpl;
import ru.goth.service.MythologyService;
import ru.goth.service.impl.MythologyServiceImpl;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "deleteMythology", value = "/deleteMythology")
public class DeleteMythology extends HttpServlet {

    private final MythologyService mythologyService;
    private static final Logger logger = Logger.getLogger(DeleteMythology.class.getName());

    public DeleteMythology() {
        this.mythologyService = new MythologyServiceImpl(new MythologyRepositoryImpl());
    }

    public DeleteMythology(MythologyService mythologyService) {
        this.mythologyService = mythologyService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            long id = Long.parseLong(request.getParameter("id"));
            boolean isDeleted = mythologyService.deleteMythology(id);

            if (isDeleted) {
                response.sendRedirect("manageMythology.jsp?success=delete");
            } else {
                response.sendRedirect("manageMythology.jsp?error=not_found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("manageMythology.jsp?error=invalid_id");
        } catch (Exception e) {
            logger.severe("Error deleting mythology: " + e.getMessage());
            response.sendRedirect("manageMythology.jsp?error=server_error");
        }
    }
}
