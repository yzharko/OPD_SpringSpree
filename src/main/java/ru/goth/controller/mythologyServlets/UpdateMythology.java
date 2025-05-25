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

@WebServlet(name = "updateMythology", value = "/updateMythology")
public class UpdateMythology extends HttpServlet {

    private final MythologyService mythologyService;

    public UpdateMythology() {
        this.mythologyService = new MythologyServiceImpl(new MythologyRepositoryImpl());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            long id = Long.parseLong(request.getParameter("id"));
            String name = request.getParameter("name");

            MythologyDto mythologyDto = new MythologyDto(name);
            mythologyDto.setId(id);

            MythologyDto updatedMythology = mythologyService.updateMythology(id, mythologyDto);

            if (updatedMythology != null) {
                response.sendRedirect("manageMythology.jsp?success=Mythology+updated");
            } else {
                response.sendRedirect("updateMythology.jsp?error=Mythology+not+found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("updateMythology.jsp?error=Invalid+ID");
        } catch (Exception e) {
            response.sendRedirect("updateMythologyMythology.jsp?error=Server+error");
        }
    }
}
