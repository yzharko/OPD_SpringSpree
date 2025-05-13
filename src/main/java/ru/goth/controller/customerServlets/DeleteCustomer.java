package ru.goth.controller.customerServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.service.CustomerService;
import ru.goth.service.impl.CustomerServiceImpl;
import ru.goth.repository.impl.CustomerRepositoryImpl;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "deleteCustomer", value = "/deleteCustomer")
public class DeleteCustomer extends HttpServlet {

    private final CustomerService customerService;
    private static final Logger logger = Logger.getLogger(ru.goth.controller.customerServlets.DeleteCustomer.class.getName());

    public DeleteCustomer() {
        this.customerService = new CustomerServiceImpl(new CustomerRepositoryImpl());
    }

    public DeleteCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            long id = Long.parseLong(request.getParameter("id"));
            boolean isDeleted = customerService.deleteCustomer(id);

            if (isDeleted) {
                response.sendRedirect("manageCustomer.jsp?success=delete");
            } else {
                response.sendRedirect("manageCustomer.jsp?error=not_found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("manageCustomer.jsp?error=invalid_id");
        } catch (Exception e) {
            logger.severe("Error deleting customer: " + e.getMessage());
            response.sendRedirect("manageCustomer.jsp?error=server_error");
        }
    }
}
