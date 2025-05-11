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

@WebServlet(name = "updateStep", value = "/updateStep")
public class UpdateStep extends HttpServlet {

    private final StepService stepService;

    public UpdateStep() { this.stepService = new StepServiceImpl(new StepRepositoryImpl()); }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");

            StepDto stepDto = new StepDto(name, description);
            stepDto.setId(id);

            StepDto updatedStep = stepService.updateStep(id, stepDto);

            if (updatedStep != null) {
                response.sendRedirect("manageStep.jsp?success=Step+updated");
            } else {
                response.sendRedirect("manageStep.jsp?error=Step+not+found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("manageStep.jsp?error=Invalid+ID");
        } catch (Exception e) {
            response.sendRedirect("manageStep.jsp?error=Server+error");
        }
    }
}
