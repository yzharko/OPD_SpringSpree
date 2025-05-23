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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostCustomerTest {

    private static final String TEST_NAME = "test_name";
    private static final String TEST_CITY_NAME = "New york";
    private static final String TEST_EMAIL = "test_email";
    private static final String NAME = "name";
    private static final String CITY_NAME = "cityName";
    private static final String EMAIL = "email";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private PostCustomer postCustomer;

    @BeforeEach
    void setUp() {
        postCustomer = new PostCustomer(customerService);
    }

    @Test
    void testDefaultConstructor() {
        PostCustomer servlet = new PostCustomer();
        assertNotNull(servlet);
    }

    @Test
    void testConstructorWithService() {
        PostCustomer servlet = new PostCustomer(customerService);
        assertNotNull(servlet);
    }

    @Test
    void doPost_ShouldCreateCustomerAndRedirectOnSuccess() throws IOException {
        lenient().when(request.getParameter(CITY_NAME)).thenReturn(TEST_CITY_NAME);
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(EMAIL)).thenReturn(TEST_EMAIL);

        CustomerDto expectedDto = new CustomerDto(TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);
        lenient().when(customerService.createCustomer(any(CustomerDto.class))).thenReturn(expectedDto);

        postCustomer.doPost(request, response);

        verify(customerService).createCustomer(any(CustomerDto.class));
        verify(response).sendRedirect("manageCustomer.jsp?success=true");
    }

    @Test
    void doPost_ShouldHandleMissingParameters() throws IOException {
        lenient().when(request.getParameter(CITY_NAME)).thenReturn(null);
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(EMAIL)).thenReturn(TEST_EMAIL);

        postCustomer.doPost(request, response);

        verify(response).sendRedirect("postCustomer.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleServiceException() throws IOException {
        lenient().when(request.getParameter(CITY_NAME)).thenReturn(TEST_CITY_NAME);
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(EMAIL)).thenReturn(TEST_EMAIL);

        lenient().when(customerService.createCustomer(any(CustomerDto.class)))
                .thenThrow(new RuntimeException("Service error"));

        postCustomer.doPost(request, response);

        verify(response).sendRedirect("postCustomer.jsp?error=true");
    }

    @Test
    void doPost_ShouldHandleIOException() throws IOException {
        lenient().when(request.getParameter(CITY_NAME)).thenReturn(TEST_CITY_NAME);
        lenient().when(request.getParameter(NAME)).thenReturn(TEST_NAME);
        lenient().when(request.getParameter(EMAIL)).thenReturn(TEST_EMAIL);

        CustomerDto expectedDto = new CustomerDto(TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);
        lenient().when(customerService.createCustomer(any(CustomerDto.class))).thenReturn(expectedDto);

        doThrow(new IOException("Redirect failed")).when(response).sendRedirect(anyString());

        assertThrows(IOException.class, () -> postCustomer.doPost(request, response));
    }
}
