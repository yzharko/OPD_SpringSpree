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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UpdateStepTest {

    private static final long TEST_ID = 1L;
    private static final String TEST_NAME = "Test Step";
    private static final String TEST_DESCRIPTION = "Test Description";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private StepService stepService;

    @InjectMocks
    private UpdateStep updateStep;

    @BeforeEach
    void setUp() {
        updateStep = new UpdateStep(stepService);
    }

    @Test
    void postStepDefaultConstructor_ShouldInitializeStepService() {
        UpdateStep updateStep = new UpdateStep();

        assertNotNull(updateStep);

        try {
            Field field = UpdateStep.class.getDeclaredField("stepService");
            field.setAccessible(true);
            StepService service = (StepService) field.get(updateStep);

            assertNotNull(service);
            assertTrue(service instanceof StepServiceImpl);
        } catch (Exception e) {
            fail("Failed to access StepService field", e);
        }
    }

    @Test
    void doPost_ShouldUpdateStepAndRedirectOnSuccess() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("description")).thenReturn(TEST_DESCRIPTION);

        StepDto expectedDto = new StepDto(TEST_NAME, TEST_DESCRIPTION);
        expectedDto.setId(TEST_ID);
        lenient().when(stepService.updateStep(eq(TEST_ID), any(StepDto.class))).thenReturn(expectedDto);

        updateStep.doPost(request, response);

        verify(stepService).updateStep(eq(TEST_ID), any(StepDto.class));
        verify(response).sendRedirect("manageStep.jsp?success=Step+updated");
    }

    @Test
    void doPost_ShouldHandleStepNotFound() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("description")).thenReturn(TEST_DESCRIPTION);

        lenient().when(stepService.updateStep(eq(TEST_ID), any(StepDto.class))).thenReturn(null);

        updateStep.doPost(request, response);

        verify(response).sendRedirect("updateStep.jsp?error=Step+not+found");
    }

    @Test
    void doPost_ShouldHandleInvalidIdFormat() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn("invalid_id");
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("description")).thenReturn(TEST_DESCRIPTION);

        updateStep.doPost(request, response);

        verify(stepService, never()).updateStep(anyLong(), any());
        verify(response).sendRedirect("updateStep.jsp?error=Invalid+ID+or+description");
    }

    @Test
    void doPost_ShouldHandleMissingNameParameter() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(null);
        lenient().when(request.getParameter("description")).thenReturn(TEST_DESCRIPTION);

        updateStep.doPost(request, response);

        verify(response).sendRedirect("updateStep.jsp?error=Server+error");
    }

    @Test
    void doPost_ShouldHandleServiceException() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("description")).thenReturn(TEST_DESCRIPTION);

        lenient().when(stepService.updateStep(eq(TEST_ID), any(StepDto.class)))
                .thenThrow(new RuntimeException("Database error"));

        updateStep.doPost(request, response);

        verify(response).sendRedirect("updateStep.jsp?error=Server+error");
    }

    @Test
    void doPost_ShouldHandleIOException() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("description")).thenReturn(TEST_DESCRIPTION);

        StepDto expectedDto = new StepDto(TEST_NAME, TEST_DESCRIPTION);
        expectedDto.setId(TEST_ID);
        lenient().when(stepService.updateStep(eq(TEST_ID), any(StepDto.class))).thenReturn(expectedDto);

        doThrow(new IOException("Redirect failed")).when(response).sendRedirect(anyString());

        assertThrows(IOException.class, () -> updateStep.doPost(request, response));
    }
}

