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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.anyString;

@ExtendWith(MockitoExtension.class)
public class PostCityTest {

    private static final String TEST_NAME = "test_city";
    private static final long TEST_DELIVERY_TIME = 5L;
    private static final String NAME = "name";
    private static final String DELIVERY_TIME = "deliveryTime";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CityService cityService;

    @InjectMocks
    private PostCity postCity;

    @BeforeEach
    void setUp() {
        postCity = new PostCity(cityService);
    }

    @Test
    void postCityDefaultConstructor_ShouldInitializeCityService() {
        PostCity postCity = new PostCity();

        assertNotNull(postCity);

        try {
            Field field = PostCity.class.getDeclaredField("cityService");
            field.setAccessible(true);
            CityService service = (CityService) field.get(postCity);

            assertNotNull(service);
            assertTrue(service instanceof CityServiceImpl);
        } catch (Exception e) {
            fail("Failed to access cityService field", e);
        }
    }

    @Test
    void doPost_ShouldCreateCityAndRedirectOnSuccess() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(DELIVERY_TIME)).thenReturn(String.valueOf(TEST_DELIVERY_TIME));

        CityDto expectedDto = new CityDto(TEST_NAME, TEST_DELIVERY_TIME);
        lenient().when(cityService.createCity(any(CityDto.class))).thenReturn(expectedDto);

        postCity.doPost(request, response);

        verify(cityService).createCity(any(CityDto.class));
        verify(response).sendRedirect("manageCity.jsp?success=true");
    }

    @Test
    void doPost_ShouldHandleMissingNameParameter() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(null);
        lenient().when(request.getParameter(DELIVERY_TIME)).thenReturn(String.valueOf(TEST_DELIVERY_TIME));

        postCity.doPost(request, response);

        verify(response).sendRedirect("postCity.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleMissingDeliveryTimeParameter() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(DELIVERY_TIME)).thenReturn(null);

        postCity.doPost(request, response);

        verify(response).sendRedirect("postCity.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleInvalidDeliveryTimeFormat() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(DELIVERY_TIME)).thenReturn("invalid_number");

        postCity.doPost(request, response);

        verify(response).sendRedirect("postCity.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleServiceException() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(DELIVERY_TIME)).thenReturn(String.valueOf(TEST_DELIVERY_TIME));

        lenient().when(cityService.createCity(any(CityDto.class)))
                .thenThrow(new RuntimeException("Service error"));

        postCity.doPost(request, response);

        verify(response).sendRedirect("postCity.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleIOException() throws IOException {
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(DELIVERY_TIME)).thenReturn(String.valueOf(TEST_DELIVERY_TIME));

        CityDto expectedDto = new CityDto(TEST_NAME, TEST_DELIVERY_TIME);
        lenient().when(cityService.createCity(any(CityDto.class))).thenReturn(expectedDto);

        doThrow(new IOException("Redirect failed")).when(response).sendRedirect(anyString());

        assertThrows(IOException.class, () -> postCity.doPost(request, response));
    }
}
