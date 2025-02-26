package ru.goth.controller.cityServlets;

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

import ru.goth.domain.dto.CityDto;
import ru.goth.service.CityService;
import ru.goth.service.impl.CityServiceImpl;

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
class GetCityTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_CITY_NAME = "Test City";
    private static final long TEST_DELIVERY_TIME = 5L;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CityService cityService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private GetCity getCity;

    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setUp() throws IOException {
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        lenient().when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    void defaultConstructor_ShouldInitializeCityService() {
        GetCity getCity = new GetCity();

        assertNotNull(getCity);

        try {
            Field field = GetCity.class.getDeclaredField("cityService");
            field.setAccessible(true);
            CityService service = (CityService) field.get(getCity);

            assertNotNull(service);
            assertTrue(service instanceof CityServiceImpl);
        } catch (Exception e) {
            fail("Failed to access cityService field", e);
        }
    }

    @Test
    void doGet_ShouldReturnAllCities() throws Exception {
        lenient().when(request.getParameter("action")).thenReturn("all");
        lenient().when(request.getRequestDispatcher("/getCity.jsp")).thenReturn(requestDispatcher);

        CityDto cityDto = new CityDto(TEST_CITY_NAME, TEST_DELIVERY_TIME);
        lenient().when(cityService.getAllCities()).thenReturn(List.of(cityDto));

        getCity.doGet(request, response);

        verify(request).setAttribute("cities", List.of(cityDto));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_ShouldReturnCityById() throws Exception {
        lenient().when(request.getParameter("id")).thenReturn(TEST_ID.toString());
        lenient().when(request.getRequestDispatcher("/getCity.jsp")).thenReturn(requestDispatcher);

        CityDto cityDto = new CityDto(TEST_CITY_NAME, TEST_DELIVERY_TIME);
        lenient().when(cityService.getCityById(TEST_ID)).thenReturn(cityDto);

        getCity.doGet(request, response);

        verify(request).setAttribute("cities", List.of(cityDto));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_ShouldReturnNotFoundWhenCityDoesNotExist() throws Exception {
        lenient().when(request.getParameter("id")).thenReturn(TEST_ID.toString());
        lenient().when(cityService.getCityById(TEST_ID)).thenReturn(null);

        getCity.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertEquals("{\"error\":\"City not found\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldReturnBadRequestWhenMissingParameters() throws Exception {

        getCity.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertEquals("{\"error\":\"Missing parameters\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldReturnBadRequestForInvalidIdFormat() throws Exception {
        lenient().when(request.getParameter("id")).thenReturn("invalid");

        getCity.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertEquals("{\"error\":\"Invalid ID format\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldHandleServiceException() throws Exception {
        lenient().when(request.getParameter("action")).thenReturn("all");
        lenient().when(cityService.getAllCities()).thenThrow(new RuntimeException("Database error"));

        getCity.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        assertEquals("{\"error\":\"Server error\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldHandleIOException() throws Exception {
        lenient().when(request.getParameter("action")).thenReturn("all");
        lenient().when(cityService.getAllCities()).thenReturn(Collections.emptyList());
        lenient().when(request.getRequestDispatcher("/getCity.jsp")).thenReturn(requestDispatcher);

        doThrow(new IOException("Forward failed")).when(requestDispatcher).forward(request, response);

        getCity.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        assertEquals("{\"error\":\"Server error\"}", stringWriter.toString());
    }
}
