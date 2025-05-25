package ru.goth.controller.cityServlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.goth.domain.dto.CityDto;
import ru.goth.service.CityService;
import ru.goth.service.impl.CityServiceImpl;

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
class UpdateCityTest {

    private static final long TEST_ID = 1L;
    private static final String TEST_NAME = "Test City";
    private static final long TEST_DELIVERY_TIME = 5L;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CityService cityService;

    @InjectMocks
    private UpdateCity updateCity;

    @BeforeEach
    void setUp() {
        updateCity = new UpdateCity(cityService);
    }

    @Test
    void postCityDefaultConstructor_ShouldInitializeCityService() {
        UpdateCity updateCity = new UpdateCity();

        assertNotNull(updateCity);

        try {
            Field field = UpdateCity.class.getDeclaredField("cityService");
            field.setAccessible(true);
            CityService service = (CityService) field.get(updateCity);

            assertNotNull(service);
            assertTrue(service instanceof CityServiceImpl);
        } catch (Exception e) {
            fail("Failed to access cityService field", e);
        }
    }

    @Test
    void doPost_ShouldUpdateCityAndRedirectOnSuccess() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("deliveryTime")).thenReturn(String.valueOf(TEST_DELIVERY_TIME));

        CityDto expectedDto = new CityDto(TEST_NAME, TEST_DELIVERY_TIME);
        expectedDto.setId(TEST_ID);
        lenient().when(cityService.updateCity(eq(TEST_ID), any(CityDto.class))).thenReturn(expectedDto);

        updateCity.doPost(request, response);

        verify(cityService).updateCity(eq(TEST_ID), any(CityDto.class));
        verify(response).sendRedirect("manageCity.jsp?success=City+updated");
    }

    @Test
    void doPost_ShouldHandleCityNotFound() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("deliveryTime")).thenReturn(String.valueOf(TEST_DELIVERY_TIME));

        lenient().when(cityService.updateCity(eq(TEST_ID), any(CityDto.class))).thenReturn(null);

        updateCity.doPost(request, response);

        verify(response).sendRedirect("updateCity.jsp?error=City+not+found");
    }

    @Test
    void doPost_ShouldHandleInvalidIdFormat() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn("invalid_id");
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("deliveryTime")).thenReturn(String.valueOf(TEST_DELIVERY_TIME));

        updateCity.doPost(request, response);

        verify(cityService, never()).updateCity(anyLong(), any());
        verify(response).sendRedirect("updateCity.jsp?error=Invalid+ID+or+delivery+time");
    }

    @Test
    void doPost_ShouldHandleInvalidDeliveryTimeFormat() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("deliveryTime")).thenReturn("invalid_time");

        updateCity.doPost(request, response);

        verify(cityService, never()).updateCity(anyLong(), any());
        verify(response).sendRedirect("updateCity.jsp?error=Invalid+ID+or+delivery+time");
    }

    @Test
    void doPost_ShouldHandleMissingNameParameter() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(null);
        lenient().when(request.getParameter("deliveryTime")).thenReturn(String.valueOf(TEST_DELIVERY_TIME));

        updateCity.doPost(request, response);

        verify(response).sendRedirect("updateCity.jsp?error=Server+error");
    }

    @Test
    void doPost_ShouldHandleServiceException() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("deliveryTime")).thenReturn(String.valueOf(TEST_DELIVERY_TIME));

        lenient().when(cityService.updateCity(eq(TEST_ID), any(CityDto.class)))
                .thenThrow(new RuntimeException("Database error"));

        updateCity.doPost(request, response);

        verify(response).sendRedirect("updateCity.jsp?error=Server+error");
    }

    @Test
    void doPost_ShouldHandleIOException() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("deliveryTime")).thenReturn(String.valueOf(TEST_DELIVERY_TIME));

        CityDto expectedDto = new CityDto(TEST_NAME, TEST_DELIVERY_TIME);
        expectedDto.setId(TEST_ID);
        lenient().when(cityService.updateCity(eq(TEST_ID), any(CityDto.class))).thenReturn(expectedDto);

        doThrow(new IOException("Redirect failed")).when(response).sendRedirect(anyString());

        assertThrows(IOException.class, () -> updateCity.doPost(request, response));
    }
}
