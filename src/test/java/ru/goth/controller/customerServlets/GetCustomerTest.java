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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetCustomerTest {

    private static final Long TEST_ID = 1L;
    private static final Long TEST_CITY_ID = 10L;
    private static final String TEST_NAME = "Pisun";
    private static final String TEST_EMAIL = "PsarevSOSAT@example.com";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private GetCustomer getCustomer;

    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setUp() throws IOException {
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        lenient().when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    void defaultConstructor_ShouldInitializeCustomerService() {
        GetCustomer getCustomer = new GetCustomer();

        assertNotNull(getCustomer);

        try {
            Field field = GetCustomer.class.getDeclaredField("customerService");
            field.setAccessible(true);
            CustomerService service = (CustomerService) field.get(getCustomer);

            assertNotNull(service);
        } catch (Exception e) {
            fail("Failed to access customerService field", e);
        }
    }

    @Test
    void doGet_ShouldReturnAllCustomers() throws Exception {
        lenient().when(request.getParameter("action")).thenReturn("all");

        CustomerDto customerDto = new CustomerDto(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        customerDto.setId(TEST_ID);
        lenient().when(customerService.getAllCustomers()).thenReturn(List.of(customerDto));

        getCustomer.doGet(request, response);

        verify(response).setContentType("application/json");
        String responseJson = stringWriter.toString();
        assertTrue(responseJson.contains(TEST_NAME));
        assertTrue(responseJson.contains(TEST_EMAIL));
        assertTrue(responseJson.contains(TEST_ID.toString()));
        assertTrue(responseJson.contains(TEST_CITY_ID.toString()));
    }

    @Test
    void doGet_ShouldReturnCustomerById() throws Exception {
        lenient().when(request.getParameter("id")).thenReturn(TEST_ID.toString());

        CustomerDto customerDto = new CustomerDto(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        customerDto.setId(TEST_ID);
        lenient().when(customerService.getCustomerById(TEST_ID)).thenReturn(customerDto);

        getCustomer.doGet(request, response);

        verify(response).setContentType("application/json");
        String responseJson = stringWriter.toString();
        assertTrue(responseJson.contains(TEST_NAME));
        assertTrue(responseJson.contains(TEST_EMAIL));
        assertTrue(responseJson.contains(TEST_ID.toString()));
        assertTrue(responseJson.contains(TEST_CITY_ID.toString()));
    }

    @Test
    void doGet_ShouldReturnNotFoundWhenCustomerDoesNotExist() throws Exception {
        lenient().when(request.getParameter("id")).thenReturn(TEST_ID.toString());
        lenient().when(customerService.getCustomerById(TEST_ID)).thenReturn(null);

        getCustomer.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertEquals("{\"error\":\"Customer not found\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldReturnBadRequestWhenMissingParameters() throws Exception {
        getCustomer.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertEquals("{\"error\":\"Missing parameters\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldReturnBadRequestForInvalidIdFormat() throws Exception {
        lenient().when(request.getParameter("id")).thenReturn("invalid");

        getCustomer.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertEquals("{\"error\":\"Invalid ID format\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldHandleServiceException() throws Exception {
        lenient().when(request.getParameter("action")).thenReturn("all");
        lenient().when(customerService.getAllCustomers()).thenThrow(new RuntimeException("Database error"));

        getCustomer.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        assertEquals("{\"error\":\"Server error\"}", stringWriter.toString());
    }

    @Test
    void doGet_ShouldReturnEmptyListWhenNoCustomers() throws Exception {
        lenient().when(request.getParameter("action")).thenReturn("all");
        lenient().when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        getCustomer.doGet(request, response);

        verify(response).setContentType("application/json");
        assertEquals("[]", stringWriter.toString().trim());
    }
}
