package ru.goth.controller.categoryServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.repository.impl.CategoryRepositoryImpl;
import ru.goth.service.CategoryService;
import ru.goth.service.impl.CategoryServiceImpl;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "deleteCategory", value = "/deleteCategory")
public class DeleteCategory extends HttpServlet {

    private final CategoryService categoryService;
    private static final Logger logger = Logger.getLogger(DeleteCategory.class.getName());

    public DeleteCategory() {
        this.categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    }

    public DeleteCategory(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            long id = Long.parseLong(request.getParameter("id"));
            boolean isDeleted = categoryService.deleteCategory(id);

            if (isDeleted) {
                response.sendRedirect("manageCategory.jsp?success=delete");
            } else {
                response.sendRedirect("manageCategory.jsp?error=not_found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("manageCategory.jsp?error=invalid_id");
        } catch (Exception e) {
            logger.severe("Error deleting Category: " + e.getMessage());
            response.sendRedirect("manageCategory.jsp?error=server_error");
        }
    }
}
