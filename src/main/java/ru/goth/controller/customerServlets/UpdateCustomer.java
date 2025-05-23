package ru.goth.controller.customerServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.domain.dto.CustomerDto;
import ru.goth.service.CustomerService;
import ru.goth.service.impl.CustomerServiceImpl;
import ru.goth.repository.impl.CustomerRepositoryImpl;

import java.io.IOException;

@WebServlet(name = "updateCustomer", value = "/updateCustomer")
public class UpdateCustomer extends HttpServlet {

    private final CustomerService customerService;

    public UpdateCustomer() {
        this.customerService = new CustomerServiceImpl(new CustomerRepositoryImpl());
    }

    public UpdateCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            long id = Long.parseLong(request.getParameter("id"));
            String cityName = request.getParameter("cityName");
            String name = request.getParameter("name");
            String email = request.getParameter("email");

            CustomerDto customerDto = new CustomerDto(cityName, name, email);
            customerDto.setId(id);

            CustomerDto updatedCustomer = customerService.updateCustomer(id, customerDto);

            if (updatedCustomer != null) {
                response.sendRedirect("manageCustomer.jsp?success=Customer+updated");
            } else {
                response.sendRedirect("updateCustomer.jsp?error=Customer+not+found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("updateCustomer.jsp?error=Invalid+ID");
        } catch (Exception e) {
            response.sendRedirect("updateCustomer.jsp?error=Server+error");
        }
    }
}
