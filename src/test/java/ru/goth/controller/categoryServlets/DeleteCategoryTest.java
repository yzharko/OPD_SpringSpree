package ru.goth.controller.categoryServlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.goth.service.CategoryService;
import ru.goth.service.impl.CategoryServiceImpl;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryTest {

    private static final String CATEGORY_ID = "666";
    private static final String ID = "id";
    private static final Long LONG_ID = 666L;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private DeleteCategory deleteCategory;

    @Test
    void postCategoryDefaultConstructor_ShouldInitializeCategoryService(){
        DeleteCategory deleteCategory = new DeleteCategory();

        assertNotNull(deleteCategory);

        try {
            Field field = DeleteCategory.class.getDeclaredField("categoryService");
            field.setAccessible(true);
            CategoryService service = (CategoryService) field.get(deleteCategory);

            assertNotNull(service);
            assertTrue(service instanceof CategoryServiceImpl);
        } catch (Exception e) {
            fail("Failed to access CategoryService field", e);
        }
    }

    @Test
    void dpPost_ShouldDeleteCategoryAndRedirectOnSuccess() throws IOException {
        when(request.getParameter(ID)).thenReturn(CATEGORY_ID);
        when(categoryService.deleteCategory(LONG_ID)).thenReturn(true);

        deleteCategory.doPost(request, response);

        verify(categoryService).deleteCategory(LONG_ID);
        verify(response).sendRedirect("manageCategory.jsp?success=delete");
    }

    @Test
    void doPost_ShouldHandleNotFoundCategory() throws IOException {
        when(request.getParameter(ID)).thenReturn(CATEGORY_ID);
        when(categoryService.deleteCategory(LONG_ID)).thenReturn(false);

        deleteCategory.doPost(request, response);

        verify(categoryService).deleteCategory(LONG_ID);
        verify(response).sendRedirect("manageCategory.jsp?error=not_found");
    }

    @Test
    void doPost_ShouldHandleInvalidIdFormat() throws IOException {
        when(request.getParameter(ID)).thenReturn("invalid");

        deleteCategory.doPost(request, response);

        verify(categoryService, never()).deleteCategory(anyLong());
        verify(response).sendRedirect("manageCategory.jsp?error=invalid_id");
    }

    @Test
    void doPost_ShouldHandleServiceException() throws IOException {
        when(request.getParameter(ID)).thenReturn(CATEGORY_ID);
        when(categoryService.deleteCategory(LONG_ID))
                .thenThrow(new RuntimeException("Database error"));

        deleteCategory.doPost(request, response);

        verify(categoryService).deleteCategory(LONG_ID);
        verify(response).sendRedirect("manageCategory.jsp?error=server_error");
    }

    @Test
    void doPost_ShouldHandleIOException() throws IOException {
        when(request.getParameter(ID)).thenReturn(CATEGORY_ID);
        when(categoryService.deleteCategory(LONG_ID)).thenReturn(true);
        doThrow(new IOException("Redirect failed")).when(response).sendRedirect(anyString());

        assertThrows(IOException.class, () -> deleteCategory.doPost(request, response));
    }
}
