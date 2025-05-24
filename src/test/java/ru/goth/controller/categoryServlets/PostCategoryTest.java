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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.anyString;

@ExtendWith(MockitoExtension.class)
public class PostCategoryTest {

    private static final String TEST_NAME = "Potion";
    private static final String TEST_HAZARD = "Low";
    private static final  String TEST_RARITY = "Common";

    private static final String NAME = "name";
    private static final String HAZARD = "hazard";
    private static final  String RARITY = "rarity";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private PostCategory postCategory;

    @BeforeEach
    void setUp() {
        postCategory = new PostCategory(categoryService);
    }

    @Test
    void postCategoryDefaultConstructor_ShouldInitializeCategoryService() {
        PostCategory postCategory = new PostCategory();

        assertNotNull(postCategory);

        try {
            Field field = PostCategory.class.getDeclaredField("categoryService");
            field.setAccessible(true);
            CategoryService service = (CategoryService) field.get(postCategory);

            assertNotNull(service);
            assertTrue(service instanceof CategoryServiceImpl);
        } catch (Exception e) {
            fail("Failed to access categoryService field", e);
        }
    }

    @Test
    void doPost_ShouldCreateCategoryAndRedirectOnSuccess() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(HAZARD)).thenReturn(TEST_HAZARD);
        lenient().when(request.getParameter(RARITY)).thenReturn(TEST_RARITY);

        CategoryDto expectedDto = new CategoryDto(TEST_NAME, TEST_HAZARD, TEST_RARITY);
        lenient().when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(expectedDto);

        postCategory.doPost(request, response);

        verify(categoryService).createCategory(any(CategoryDto.class));
        verify(response).sendRedirect("manageCategory.jsp?success=true");
    }

    @Test
    void doPost_ShouldHandleMissingNameParameter() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(null);
        lenient().when(request.getParameter(HAZARD)).thenReturn(TEST_HAZARD);
        lenient().when(request.getParameter(RARITY)).thenReturn(TEST_RARITY);

        postCategory.doPost(request, response);

        verify(response).sendRedirect("postCategory.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleMissingHazardParameter() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(HAZARD)).thenReturn(null);
        lenient().when(request.getParameter(RARITY)).thenReturn(TEST_RARITY);

        postCategory.doPost(request, response);

        verify(response).sendRedirect("postCategory.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleMissingRarityParameter() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(HAZARD)).thenReturn(TEST_HAZARD);
        lenient().when(request.getParameter(RARITY)).thenReturn(null);

        postCategory.doPost(request, response);

        verify(response).sendRedirect("postCategory.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleServiceException() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(HAZARD)).thenReturn(TEST_HAZARD);
        lenient().when(request.getParameter(RARITY)).thenReturn(TEST_RARITY);

        lenient().when(categoryService.createCategory(any(CategoryDto.class)))
                .thenThrow(new RuntimeException("Service error"));

        postCategory.doPost(request, response);

        verify(response).sendRedirect("postCategory.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleIOException() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(HAZARD)).thenReturn(TEST_HAZARD);
        lenient().when(request.getParameter(RARITY)).thenReturn(TEST_RARITY);

        CategoryDto expectedDto = new CategoryDto(TEST_NAME, TEST_HAZARD, TEST_RARITY);
        lenient().when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(expectedDto);

        doThrow(new IOException("Redirect failed")).when(response).sendRedirect(anyString());

        assertThrows(IOException.class, () -> postCategory.doPost(request, response));
    }
}
