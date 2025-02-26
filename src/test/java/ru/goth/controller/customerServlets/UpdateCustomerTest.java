package ru.goth.controller.customerServlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.goth.domain.dto.CustomerDto;
import ru.goth.service.CustomerService;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerTest {

    private static final long TEST_ID = 1L;
    private static final String TEST_CITY_NAME = "Test City";
    private static final String TEST_NAME = "Test Customer";
    private static final String TEST_EMAIL = "test@example.com";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private UpdateCustomer updateCustomer;

    @BeforeEach
    void setUp() {
        updateCustomer = new UpdateCustomer(customerService);
    }

    @Test
    void postCustomerDefaultConstructor_ShouldInitializeCustomerService() {
        UpdateCustomer updateCustomer = new UpdateCustomer();

        assertNotNull(updateCustomer);

        try {
            Field field = UpdateCustomer.class.getDeclaredField("customerService");
            field.setAccessible(true);
            CustomerService service = (CustomerService) field.get(updateCustomer);

            assertNotNull(service);
        } catch (Exception e) {
            fail("Failed to access customerService field", e);
        }
    }

    @Test
    void doPost_ShouldUpdateCustomerAndRedirectOnSuccess() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("cityName")).thenReturn(TEST_CITY_NAME);
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("email")).thenReturn(TEST_EMAIL);

        CustomerDto expectedDto = new CustomerDto(TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);
        expectedDto.setId(TEST_ID);
        lenient().when(customerService.updateCustomer(eq(TEST_ID), any(CustomerDto.class))).thenReturn(expectedDto);

        updateCustomer.doPost(request, response);

        verify(customerService).updateCustomer(eq(TEST_ID), any(CustomerDto.class));
        verify(response).sendRedirect("manageCustomer.jsp?success=Customer+updated");
    }

    @Test
    void doPost_ShouldHandleCustomerNotFound() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("cityName")).thenReturn(TEST_CITY_NAME);
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("email")).thenReturn(TEST_EMAIL);

        lenient().when(customerService.updateCustomer(eq(TEST_ID), any(CustomerDto.class))).thenReturn(null);

        updateCustomer.doPost(request, response);

        verify(response).sendRedirect("updateCustomer.jsp?error=Customer+not+found");
    }

    @Test
    void doPost_ShouldHandleInvalidIdFormat() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn("invalid_id");
        lenient().when(request.getParameter("cityName")).thenReturn(TEST_CITY_NAME);
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("email")).thenReturn(TEST_EMAIL);

        updateCustomer.doPost(request, response);

        verify(customerService, never()).updateCustomer(anyLong(), any());
        verify(response).sendRedirect("updateCustomer.jsp?error=Invalid+ID");
    }

    @Test
    void doPost_ShouldHandleMissingParameters() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("cityName")).thenReturn(null);
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("email")).thenReturn(TEST_EMAIL);

        updateCustomer.doPost(request, response);

        verify(response).sendRedirect("updateCustomer.jsp?error=Server+error");
    }

    @Test
    void doPost_ShouldHandleServiceException() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("cityName")).thenReturn(TEST_CITY_NAME);
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("email")).thenReturn(TEST_EMAIL);

        lenient().when(customerService.updateCustomer(eq(TEST_ID), any(CustomerDto.class)))
                .thenThrow(new RuntimeException("Database error"));

        updateCustomer.doPost(request, response);

        verify(response).sendRedirect("updateCustomer.jsp?error=Server+error");
    }

    @Test
    void doPost_ShouldHandleIOException() throws IOException {
        lenient().when(request.getParameter("id")).thenReturn(String.valueOf(TEST_ID));
        lenient().when(request.getParameter("cityName")).thenReturn(TEST_CITY_NAME);
        lenient().when(request.getParameter("name")).thenReturn(TEST_NAME);
        lenient().when(request.getParameter("email")).thenReturn(TEST_EMAIL);

        CustomerDto expectedDto = new CustomerDto(TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);
        expectedDto.setId(TEST_ID);
        lenient().when(customerService.updateCustomer(eq(TEST_ID), any(CustomerDto.class))).thenReturn(expectedDto);

        doThrow(new IOException("Redirect failed")).when(response).sendRedirect(anyString());

        assertThrows(IOException.class, () -> updateCustomer.doPost(request, response));
    }
}
