package ru.goth.controller.cityServlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.goth.service.CityService;
import ru.goth.service.impl.CityServiceImpl;

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
class DeleteCityTest {

    private static final String CITY_ID = "123";
    private static final String ID = "id";
    private static final Long LONG_ID = 123L;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CityService cityService;

    @InjectMocks
    private DeleteCity deleteCity;

    @Test
    void postCityDefaultConstructor_ShouldInitializeCityService() {
        DeleteCity deleteCity = new DeleteCity();

        assertNotNull(deleteCity);

        try {
            Field field = DeleteCity.class.getDeclaredField("cityService");
            field.setAccessible(true);
            CityService service = (CityService) field.get(deleteCity);

            assertNotNull(service);
            assertTrue(service instanceof CityServiceImpl);
        } catch (Exception e) {
            fail("Failed to access cityService field", e);
        }
    }

    @Test
    void doPost_ShouldDeleteCityAndRedirectOnSuccess() throws IOException {
        when(request.getParameter(ID)).thenReturn(CITY_ID);
        when(cityService.deleteCity(LONG_ID)).thenReturn(true);

        deleteCity.doPost(request, response);

        verify(cityService).deleteCity(LONG_ID);
        verify(response).sendRedirect("manageCity.jsp?success=delete");
    }

    @Test
    void doPost_ShouldHandleNotFoundCity() throws IOException {
        when(request.getParameter(ID)).thenReturn(CITY_ID);
        when(cityService.deleteCity(LONG_ID)).thenReturn(false);

        deleteCity.doPost(request, response);

        verify(cityService).deleteCity(LONG_ID);
        verify(response).sendRedirect("manageCity.jsp?error=not_found");
    }

    @Test
    void doPost_ShouldHandleInvalidIdFormat() throws IOException {
        when(request.getParameter(ID)).thenReturn("invalid");

        deleteCity.doPost(request, response);

        verify(cityService, never()).deleteCity(anyLong());
        verify(response).sendRedirect("manageCity.jsp?error=invalid_id");
    }

    @Test
    void doPost_ShouldHandleServiceException() throws IOException {
        when(request.getParameter(ID)).thenReturn(CITY_ID);
        when(cityService.deleteCity(LONG_ID))
                .thenThrow(new RuntimeException("Database error"));

        deleteCity.doPost(request, response);

        verify(cityService).deleteCity(LONG_ID);
        verify(response).sendRedirect("manageCity.jsp?error=server_error");
    }

    @Test
    void doPost_ShouldHandleIOException() throws IOException {
        when(request.getParameter(ID)).thenReturn(CITY_ID);
        when(cityService.deleteCity(LONG_ID)).thenReturn(true);
        doThrow(new IOException("Redirect failed")).when(response).sendRedirect(anyString());

        assertThrows(IOException.class, () -> deleteCity.doPost(request, response));
    }
}
