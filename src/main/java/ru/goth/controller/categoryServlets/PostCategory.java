package ru.goth.controller.categoryServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.domain.dto.CategoryDto;
import ru.goth.repository.impl.CategoryRepositoryImpl;
import ru.goth.service.CategoryService;
import ru.goth.service.impl.CategoryServiceImpl;

import java.io.IOException;

@WebServlet(name = "postCategory", value = "/postCategory")
public class PostCategory extends HttpServlet {

    private final CategoryService categoryService;

    public PostCategory() {
        this.categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    }

    public PostCategory(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            String name = request.getParameter("name");
            String hazard = request.getParameter("hazard");
            String rarity = request.getParameter("rarity");

            if (name == null || name.isEmpty() || hazard == null || hazard.isEmpty() || rarity == null || rarity.isEmpty()) {
                response.sendRedirect("postCategory.jsp?error=true");
                return;
            }

            CategoryDto categoryDto = new CategoryDto(name, hazard, rarity);
            categoryService.createCategory(categoryDto);

            response.sendRedirect("manageCategory.jsp?success=true");
        } catch (Exception e) {
            response.sendRedirect("postCategory.jsp?error=true");
        }
    }
}
