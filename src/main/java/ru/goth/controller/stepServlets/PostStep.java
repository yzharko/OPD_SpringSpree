package ru.goth.controller.stepServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.domain.dto.StepDto;
import ru.goth.repository.impl.StepRepositoryImpl;
import ru.goth.service.StepService;
import ru.goth.service.impl.StepServiceImpl;

import java.io.IOException;

@WebServlet(name = "postStep", value = "/postStep")
public class PostStep extends HttpServlet {

    private final StepService stepService;

    public PostStep() {this.stepService = new StepServiceImpl(new StepRepositoryImpl()); }

    public PostStep(StepService stepService) { this.stepService = stepService; }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");

            if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
                response.sendRedirect("postStep.jsp?error=true");
                return;
            }

            StepDto stepDto = new StepDto(name, description);
            stepService.createStep(stepDto);

            response.sendRedirect("manageStep.jsp?success=true");
        } catch (Exception e) {
            response.sendRedirect("postStep.jsp?error=true");
        }
    }
}
