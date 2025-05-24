package ru.goth.controller.categoryServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;

import ru.goth.domain.dto.CategoryDto;
import ru.goth.service.CategoryService;
import ru.goth.service.impl.CategoryServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GetCategoryTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "Potion";
    private static final String TEST_HAZARD = "Low";
    private static final  String TEST_RARITY = "Common";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CategoryService categoryService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private GetCategory getCategory;

    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setUp() throws IOException {
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        lenient().when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    void defaultConstructor_ShouldInitializeCategoryService() {
        GetCategory getCategory = new GetCategory();

        assertNotNull(getCategory);

        try {
            Field field = GetCategory.class.getDeclaredField("categoryService");
            field.setAccessible(true);
            CategoryService service = (CategoryService) field.get(getCategory);

            assertNotNull(service);
            assertTrue(service instanceof CategoryServiceImpl);
        } catch (Exception e) {
            fail("Failed to access categoryService field", e);
        }
    }

    @Test
    void doGet_ShouldReturnAllCategories() throws Exception {
        lenient().when(request.getParameter("action")).thenReturn("all");
        lenient().when(request.getRequestDispatcher("/getCategory.jsp")).thenReturn(requestDispatcher);

        CategoryDto categoryDto = new CategoryDto(TEST_NAME, TEST_HAZARD, TEST_RARITY);
        lenient().when(categoryService.getAllCategories()).thenReturn(List.of(categoryDto));

        getCategory.doGet(request, response);

        verify(request).setAttribute("categories", List.of(categoryDto));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_ShouldReturnCategoryById() throws Exception {
        lenient().when(request.getParameter("id")).thenReturn(TEST_ID.toString());
        lenient().when(request.getRequestDispatcher("/getCategory.jsp")).thenReturn(requestDispatcher);

        CategoryDto categoryDto = new CategoryDto(TEST_NAME, TEST_HAZARD, TEST_RARITY);
        lenient().when(categoryService.getCategoryById(TEST_ID)).thenReturn(categoryDto);

        getCategory.doGet(request, response);

        verify(request).setAttribute("categories", List.of(categoryDto));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_ShouldReturnNotFoundWhenCategoryDoesNotExist() throws Exception {
        lenient().when(request.getParameter("id")).thenReturn(TEST_ID.toString());
        lenient().when(categoryService.getCategoryById(TEST_ID)).thenReturn(null);

        getCategory.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertEquals("{\"error\":\"Category not found\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldReturnBadRequestWhenMissingParameters() throws Exception {

        getCategory.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertEquals("{\"error\":\"Missing parameters\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldReturnBadRequestForInvalidIdFormat() throws Exception {
        lenient().when(request.getParameter("id")).thenReturn("invalid");

        getCategory.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertEquals("{\"error\":\"Invalid ID format\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldHandleServiceException() throws Exception {
        lenient().when(request.getParameter("action")).thenReturn("all");
        lenient().when(categoryService.getAllCategories()).thenThrow(new RuntimeException("Database error"));

        getCategory.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        assertEquals("{\"error\":\"Server error\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldHandleIOException() throws Exception {
        lenient().when(request.getParameter("action")).thenReturn("all");
        lenient().when(categoryService.getAllCategories()).thenReturn(Collections.emptyList());
        lenient().when(request.getRequestDispatcher("/getCategory.jsp")).thenReturn(requestDispatcher);

        doThrow(new IOException("Forward failed")).when(requestDispatcher).forward(request, response);

        getCategory.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        assertEquals("{\"error\":\"Server error\"}", stringWriter.toString());
    }
}
