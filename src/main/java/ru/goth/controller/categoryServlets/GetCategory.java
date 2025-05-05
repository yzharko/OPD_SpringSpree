package ru.goth.controller.categoryServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.config.JsonConvertor;
import ru.goth.domain.dto.CategoryDto;
import ru.goth.repository.impl.CategoryRepositoryImpl;
import ru.goth.service.CategoryService;
import ru.goth.service.impl.CategoryServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "getCategory", value = "/getCategory")
public class GetCategory extends HttpServlet {

    private static final Logger logger = Logger.getLogger(GetCategory.class.getName());
    private final CategoryService categoryService;
    private final JsonConvertor<CategoryDto> categoryDtoConvertor = new JsonConvertor<>();
    private final JsonConvertor<List<CategoryDto>> categoryListConvertor = new JsonConvertor<>();

    public GetCategory() {
        this.categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    }

    public GetCategory(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        try {
            String action = request.getParameter("action");
            String idParam = request.getParameter("id");

            if ("all".equals(action)) { // тут короче либо все категории либо по ID
                List<CategoryDto> cities = categoryService.getAllCategories();
                categoryListConvertor.convertToJson(response, cities);
            } else if (idParam != null && !idParam.isEmpty()) {
                long id = Long.parseLong(idParam);
                CategoryDto category = categoryService.getCategoryById(id);

                if (category != null) {
                    categoryDtoConvertor.convertToJson(response, category);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Category not found\"}");
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
