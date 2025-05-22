package ru.goth.controller.stepServlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.goth.domain.dto.StepDto;
import ru.goth.service.StepService;
import ru.goth.service.impl.StepServiceImpl;

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
public class PostStepTest {

    private static final String TEST_NAME = "test_step";
    private static final String TEST_DESCRIPTION = "test_description";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private StepService stepService;

    @InjectMocks
    private PostStep postStep;

    @BeforeEach
    void setUp() {
        postStep = new PostStep(stepService);
    }

    @Test
    void postStepDefaultConstructor_ShouldInitializeStepService() {
        PostStep postStep = new PostStep();

        assertNotNull(postStep);

        try {
            Field field = PostStep.class.getDeclaredField("stepService");
            field.setAccessible(true);
            StepService service = (StepService) field.get(postStep);

            assertNotNull(service);
            assertTrue(service instanceof StepServiceImpl);
        } catch (Exception e) {
            fail("Failed to access stepService field", e);
        }
    }

    @Test
    void doPost_ShouldCreateStepAndRedirectOnSuccess() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(DESCRIPTION)).thenReturn(TEST_DESCRIPTION);

        StepDto expectedDto = new StepDto(TEST_NAME, TEST_DESCRIPTION);
        lenient().when(stepService.createStep(any(StepDto.class))).thenReturn(expectedDto);

        postStep.doPost(request, response);

        verify(stepService).createStep(any(StepDto.class));
        verify(response).sendRedirect("manageStep.jsp?success=true");
    }

    @Test
    void doPost_ShouldHandleMissingNameParameter() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(null);
        lenient().when(request.getParameter(DESCRIPTION)).thenReturn(TEST_DESCRIPTION);

        postStep.doPost(request, response);

        verify(response).sendRedirect("postStep.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleMissingDescriptionParameter() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(DESCRIPTION)).thenReturn(null);

        postStep.doPost(request, response);

        verify(response).sendRedirect("postStep.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleServiceException() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(DESCRIPTION)).thenReturn(TEST_DESCRIPTION);

        lenient().when(stepService.createStep(any(StepDto.class)))
                .thenThrow(new RuntimeException("Service error"));

        postStep.doPost(request, response);

        verify(response).sendRedirect("postStep.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleIOException() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(DESCRIPTION)).thenReturn(TEST_DESCRIPTION);

        StepDto expectedDto = new StepDto(TEST_NAME, TEST_DESCRIPTION);
        lenient().when(stepService.createStep(any(StepDto.class))).thenReturn(expectedDto);

        doThrow(new IOException("Redirect failed")).when(response).sendRedirect(anyString());

        assertThrows(IOException.class, () -> postStep.doPost(request, response));
    }
}
