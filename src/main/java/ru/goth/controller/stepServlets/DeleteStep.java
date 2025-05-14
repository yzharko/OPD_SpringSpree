package ru.goth.controller.stepServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.repository.impl.StepRepositoryImpl;
import ru.goth.service.StepService;
import ru.goth.service.impl.StepServiceImpl;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "deleteStep", value = "/deleteStep")
public class DeleteStep extends HttpServlet {

    private final StepService stepService;
    private static final Logger logger = Logger.getLogger(DeleteStep.class.getName());

    public DeleteStep() { this.stepService = new StepServiceImpl(new StepRepositoryImpl());}

    public DeleteStep(StepService stepService) { this.stepService = stepService;}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            boolean isDeleted = stepService.deleteStep(id);

            if (isDeleted) {
                response.sendRedirect("manageStep.jsp?success=delete");
            } else {
                response.sendRedirect("manageStep.jsp?error=not_found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("manageStep.jsp?error=invalid_id");
        } catch (Exception e) {
            logger.severe("Error deleting step: " + e.getMessage());
            response.sendRedirect("manageStep.jsp?error=server_error");
        }
    }
}
