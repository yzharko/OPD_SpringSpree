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
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "getStep", value = "/getStep")
public class GetStep extends HttpServlet {

    private static final Logger logger = Logger.getLogger(GetStep.class.getName());
    private final StepService stepService;

    public GetStep() { this.stepService = new StepServiceImpl(new StepRepositoryImpl()); }
    public GetStep(StepService stepService) { this.stepService = stepService; }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String action = request.getParameter("action");
            String stepId = request.getParameter("id");

            if ("all".equals(action)) {
                List<StepDto> steps = stepService.getAllSteps();
                request.setAttribute("steps", steps);
                request.getRequestDispatcher("/getStep.jsp").forward(request, response);
            } else if (stepId != null && !stepId.isEmpty()) {
                long id = Long.parseLong(stepId);
                StepDto step = stepService.getStepById(id);

                if (step != null) {
                    request.setAttribute("steps", List.of(step));
                    request.getRequestDispatcher("/getStep.jsp").forward(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Step not found\"}");
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
