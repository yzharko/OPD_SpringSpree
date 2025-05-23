package ru.goth.controller.categoryServlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.goth.domain.dto.CategoryDto;
import ru.goth.service.CategoryService;
import ru.goth.service.impl.CategoryServiceImpl;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryTest {

    private static final long TEST_ID = 1L;
    private static final String TEST_NAME = "Test Category";
    private static final String TEST_HAZARD = "Test Hazard";
    private static final String TEST_RARITY = "Test Rarity";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private UpdateCategory updateCategory;

    @BeforeEach
    void setUp() {
        updateCategory = new UpdateCategory(categoryService);
    }

    @Test
    void postCategoryDefaultConstructor_ShouldInitializeCategoryService() {
        UpdateCategory updateCategory = new UpdateCategory();

        assertNotNull(updateCategory);

        try {
            Field field = UpdateCategory.class.getDeclaredField("categoryService");
            field.setAccessible(true);
            CategoryService service = (CategoryService) field.get(updateCategory);

            assertNotNull(service);
            assertTrue(service instanceof CategoryServiceImpl);
        } catch (Exception e) {
            fail("Failed to access CategoryService field", e);
        }
    }

    @Test
    void doPost_ShouldUpdateCategoryAndRedirectOnSuccess() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("hazard")).thenReturn(TEST_HAZARD);
        lenient().when(request.getParameter("rarity")).thenReturn(TEST_RARITY);

        CategoryDto expectedDto = new CategoryDto(TEST_NAME, TEST_HAZARD, TEST_RARITY);
        expectedDto.setId(TEST_ID);
        lenient().when(categoryService.updateCategory(eq(TEST_ID), any(CategoryDto.class))).thenReturn(expectedDto);

        updateCategory.doPost(request, response);

        verify(categoryService).updateCategory(eq(TEST_ID), any(CategoryDto.class));
        verify(response).sendRedirect("manageCategory.jsp?success=Category+updated");
    }

    @Test
    void doPost_ShouldHandleCategoryNotFound() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("hazard")).thenReturn(TEST_HAZARD);
        lenient().when(request.getParameter("rarity")).thenReturn(TEST_RARITY);

        lenient().when(categoryService.updateCategory(eq(TEST_ID), any(CategoryDto.class))).thenReturn(null);

        updateCategory.doPost(request, response);

        verify(response).sendRedirect("updateCategory.jsp?error=Category+not+found");
    }

    @Test
    void doPost_ShouldHandleInvalidIdFormat() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn("invalid_id");
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("hazard")).thenReturn(TEST_HAZARD);
        lenient().when(request.getParameter("rarity")).thenReturn(TEST_RARITY);

        updateCategory.doPost(request, response);

        verify(categoryService, never()).updateCategory(anyLong(), any());
        verify(response).sendRedirect("updateCategory.jsp?error=Invalid+ID+or+hazard+or+rarity");
    }

    @Test
    void doPost_ShouldHandleMissingNameParameter() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(null);
        lenient().when(request.getParameter("hazard")).thenReturn(TEST_HAZARD);
        lenient().when(request.getParameter("rarity")).thenReturn(TEST_RARITY);

        updateCategory.doPost(request, response);

        verify(response).sendRedirect("updateCategory.jsp?error=Server+error");
    }

    @Test
    void doPost_ShouldHandleServiceException() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("hazard")).thenReturn(TEST_HAZARD);
        lenient().when(request.getParameter("rarity")).thenReturn(TEST_RARITY);

        lenient().when(categoryService.updateCategory(eq(TEST_ID), any(CategoryDto.class)))
                .thenThrow(new RuntimeException("Database error"));

        updateCategory.doPost(request, response);

        verify(response).sendRedirect("updateCategory.jsp?error=Server+error");
    }

    @Test
    void doPost_ShouldHandleIOException() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("hazard")).thenReturn(TEST_HAZARD);
        lenient().when(request.getParameter("rarity")).thenReturn(TEST_RARITY);

        CategoryDto expectedDto = new CategoryDto(TEST_NAME, TEST_HAZARD, TEST_RARITY);
        expectedDto.setId(TEST_ID);
        lenient().when(categoryService.updateCategory(eq(TEST_ID), any(CategoryDto.class))).thenReturn(expectedDto);

        doThrow(new IOException("Redirect failed")).when(response).sendRedirect(anyString());

        assertThrows(IOException.class, () -> updateCategory.doPost(request, response));
    }
}


