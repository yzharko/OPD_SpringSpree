package ru.goth.controller.stepServlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.goth.service.StepService;
import ru.goth.service.impl.StepServiceImpl;

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
class DeleteStepTest {

    private static final String STEP_ID = "123";
    private static final String ID = "id";
    private static final Long LONG_ID = 123L;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private StepService stepService;

    @InjectMocks
    private DeleteStep deleteStep;

    @Test
    void postStepDefaultConstructor_ShouldInitializeStepService() {
        DeleteStep deleteStep = new DeleteStep();

        assertNotNull(deleteStep);

        try {
            Field field = DeleteStep.class.getDeclaredField("stepService");
            field.setAccessible(true);
            StepService service = (StepService) field.get(deleteStep);

            assertNotNull(service);
            assertTrue(service instanceof StepServiceImpl);
        } catch (Exception e) {
            fail("Failed to access stepService field", e);
        }
    }

    @Test
    void doPost_ShouldDeleteStepAndRedirectOnSuccess() throws IOException {
        when(request.getParameter(ID)).thenReturn(STEP_ID);
        when(stepService.deleteStep(LONG_ID)).thenReturn(true);

        deleteStep.doPost(request, response);

        verify(stepService).deleteStep(LONG_ID);
        verify(response).sendRedirect("manageStep.jsp?success=delete");
    }

    @Test
    void doPost_ShouldHandleNotFoundStep() throws IOException {
        when(request.getParameter(ID)).thenReturn(STEP_ID);
        when(stepService.deleteStep(LONG_ID)).thenReturn(false);

        deleteStep.doPost(request, response);

        verify(stepService).deleteStep(LONG_ID);
        verify(response).sendRedirect("manageStep.jsp?error=not_found");
    }

    @Test
    void doPost_ShouldHandleInvalidIdFormat() throws IOException {
        when(request.getParameter(ID)).thenReturn("invalid");

        deleteStep.doPost(request, response);

        verify(stepService, never()).deleteStep(anyLong());
        verify(response).sendRedirect("manageStep.jsp?error=invalid_id");
    }

    @Test
    void doPost_ShouldHandleServiceException() throws IOException {
        when(request.getParameter(ID)).thenReturn(STEP_ID);
        when(stepService.deleteStep(LONG_ID))
                .thenThrow(new RuntimeException("Database error"));

        deleteStep.doPost(request, response);

        verify(stepService).deleteStep(LONG_ID);
        verify(response).sendRedirect("manageStep.jsp?error=server_error");
    }

    @Test
    void doPost_ShouldHandleIOException() throws IOException {
        when(request.getParameter(ID)).thenReturn(STEP_ID);
        when(stepService.deleteStep(LONG_ID)).thenReturn(true);
        doThrow(new IOException("Redirect failed")).when(response).sendRedirect(anyString());

        assertThrows(IOException.class, () -> deleteStep.doPost(request, response));
    }
}
