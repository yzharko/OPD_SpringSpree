package ru.goth.controller.customerServlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.goth.service.CustomerService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerTest {

    private static final String CUSTOMER_ID = "123";
    private static final String ID = "id";
    private static final Long LONG_ID = 123L;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private DeleteCustomer deleteCustomer;

    @Test
    void testDefaultConstructor() {
        DeleteCustomer servlet = new DeleteCustomer();
        assertNotNull(servlet);
    }

    @Test
    void testConstructorWithService() {
        DeleteCustomer servlet = new DeleteCustomer(customerService);
        assertNotNull(servlet);
    }

    @Test
    void doPost_ShouldDeleteCustomerAndRedirectOnSuccess() throws IOException {
        when(request.getParameter(ID)).thenReturn(CUSTOMER_ID);
        when(customerService.deleteCustomer(LONG_ID)).thenReturn(true);

        deleteCustomer.doPost(request, response);

        verify(customerService).deleteCustomer(LONG_ID);
        verify(response).sendRedirect("manageCustomer.jsp?success=delete");
    }

    @Test
    void doPost_ShouldHandleNotFoundCustomer() throws IOException {
        when(request.getParameter(ID)).thenReturn(CUSTOMER_ID);
        when(customerService.deleteCustomer(LONG_ID)).thenReturn(false);

        deleteCustomer.doPost(request, response);

        verify(customerService).deleteCustomer(LONG_ID);
        verify(response).sendRedirect("manageCustomer.jsp?error=not_found");
    }

    @Test
    void doPost_ShouldHandleInvalidIdFormat() throws IOException {
        when(request.getParameter(ID)).thenReturn("invalid");

        deleteCustomer.doPost(request, response);

        verify(customerService, never()).deleteCustomer(anyLong());
        verify(response).sendRedirect("manageCustomer.jsp?error=invalid_id");
    }

    @Test
    void doPost_ShouldHandleServiceException() throws IOException {
        when(request.getParameter(ID)).thenReturn(CUSTOMER_ID);
        when(customerService.deleteCustomer(LONG_ID))
                .thenThrow(new RuntimeException("Database error"));

        deleteCustomer.doPost(request, response);

        verify(customerService).deleteCustomer(LONG_ID);
        verify(response).sendRedirect("manageCustomer.jsp?error=server_error");
    }

    @Test
    void doPost_ShouldHandleIOException() throws IOException {
        when(request.getParameter(ID)).thenReturn(CUSTOMER_ID);
        when(customerService.deleteCustomer(LONG_ID)).thenReturn(true);
        doThrow(new IOException("Redirect failed")).when(response).sendRedirect(anyString());

        assertThrows(IOException.class, () -> deleteCustomer.doPost(request, response));
    }
}
