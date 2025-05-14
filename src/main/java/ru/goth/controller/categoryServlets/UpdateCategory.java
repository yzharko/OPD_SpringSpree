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

@WebServlet(name = "updateCategory", value = "/updateCategory")
public class UpdateCategory extends HttpServlet {

    private final CategoryService categoryService;

    public UpdateCategory() {
        this.categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            long id = Long.parseLong(request.getParameter("id"));
            String name = request.getParameter("name");
            String hazard = request.getParameter("hazard");
            String rarity = request.getParameter("rarity");

            CategoryDto categoryDto = new CategoryDto(name, hazard, rarity);
            categoryDto.setId(id);

            CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);

            if (updatedCategory != null) {
                response.sendRedirect("manageCategory.jsp?success=Category+updated");
            } else {
                response.sendRedirect("updateCategory.jsp?error=Category+not+found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("updateCategory.jsp?error=Invalid+ID+or+delivery+time");
        } catch (Exception e) {
            response.sendRedirect("updateCategory.jsp?error=Server+error");
        }
    }
}
