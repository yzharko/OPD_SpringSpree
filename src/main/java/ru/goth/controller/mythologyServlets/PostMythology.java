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

@WebServlet(name = "postMythology", value = "/postMythology")
public class PostMythology extends HttpServlet {

    private final MythologyService mythologyService;

    public PostMythology() {
        this.mythologyService = new MythologyServiceImpl(new MythologyRepositoryImpl());
    }

    public PostMythology(MythologyService mythologyService) {
        this.mythologyService = mythologyService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            String name = request.getParameter("name");

            MythologyDto mythologyDto = new MythologyDto(name);
            mythologyService.createMythology(mythologyDto);

            response.sendRedirect("manageMythology.jsp?success=true");
        } catch (Exception e) {
            response.sendRedirect("postMythology.jsp?error=true");
        }
    }
}
