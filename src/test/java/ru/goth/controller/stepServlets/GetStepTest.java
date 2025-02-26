package ru.goth.controller.stepServlets;

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

import ru.goth.domain.dto.StepDto;
import ru.goth.service.StepService;
import ru.goth.service.impl.StepServiceImpl;

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
class GetStepTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_STEP_NAME = "Test Step";
    private static final String TEST_STEP_DESCRIPTION = "Test Description";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private StepService stepService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private GetStep getStep;

    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setUp() throws IOException {
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        lenient().when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    void defaultConstructor_ShouldInitializeStepService() {
        GetStep getStep = new GetStep();

        assertNotNull(getStep);

        try {
            Field field = GetStep.class.getDeclaredField("stepService");
            field.setAccessible(true);
            StepService service = (StepService) field.get(getStep);

            assertNotNull(service);
            assertTrue(service instanceof StepServiceImpl);
        } catch (Exception e) {
            fail("Failed to access stepService field", e);
        }
    }

    @Test
    void doGet_ShouldReturnAllSteps() throws Exception {
        lenient().when(request.getParameter("action")).thenReturn("all");
        lenient().when(request.getRequestDispatcher("/getStep.jsp")).thenReturn(requestDispatcher);

        StepDto stepDto = new StepDto(TEST_STEP_NAME, TEST_STEP_DESCRIPTION);
        lenient().when(stepService.getAllSteps()).thenReturn(List.of(stepDto));

        getStep.doGet(request, response);

        verify(request).setAttribute("steps", List.of(stepDto));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_ShouldReturnStepById() throws Exception {
        lenient().when(request.getParameter("id")).thenReturn(TEST_ID.toString());
        lenient().when(request.getRequestDispatcher("/getStep.jsp")).thenReturn(requestDispatcher);

        StepDto stepDto = new StepDto(TEST_STEP_NAME, TEST_STEP_DESCRIPTION);
        lenient().when(stepService.getStepById(TEST_ID)).thenReturn(stepDto);

        getStep.doGet(request, response);

        verify(request).setAttribute("steps", List.of(stepDto));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_ShouldReturnNotFoundWhenStepDoesNotExist() throws Exception {
        lenient().when(request.getParameter("id")).thenReturn(TEST_ID.toString());
        lenient().when(stepService.getStepById(TEST_ID)).thenReturn(null);

        getStep.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertEquals("{\"error\":\"Step not found\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldReturnBadRequestWhenMissingParameters() throws Exception {
        getStep.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertEquals("{\"error\":\"Missing parameters\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldReturnBadRequestForInvalidIdFormat() throws Exception {
        lenient().when(request.getParameter("id")).thenReturn("invalid");

        getStep.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertEquals("{\"error\":\"Invalid ID format\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldHandleServiceException() throws Exception {
        lenient().when(request.getParameter("action")).thenReturn("all");
        lenient().when(stepService.getAllSteps()).thenThrow(new RuntimeException("Database error"));

        getStep.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        assertEquals("{\"error\":\"Server error\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldHandleIOException() throws Exception {
        lenient().when(request.getParameter("action")).thenReturn("all");
        lenient().when(stepService.getAllSteps()).thenReturn(Collections.emptyList());
        lenient().when(request.getRequestDispatcher("/getStep.jsp")).thenReturn(requestDispatcher);

        doThrow(new IOException("Forward failed")).when(requestDispatcher).forward(request, response);

        getStep.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        assertEquals("{\"error\":\"Server error\"}", stringWriter.toString());
    }
}
