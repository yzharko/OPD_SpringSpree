package ru.goth.controller.customerServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.goth.domain.dto.CustomerDto;
import ru.goth.repository.impl.CustomerRepositoryImpl;
import ru.goth.service.CustomerService;
import ru.goth.service.impl.CustomerServiceImpl;

import java.io.IOException;

@WebServlet(name = "postCustomer", value = "/postCustomer")
public class PostCustomer extends HttpServlet {

    private final CustomerService customerService;

    public PostCustomer() {
        this.customerService = new CustomerServiceImpl(new CustomerRepositoryImpl());
    }

    public PostCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            String cityName = request.getParameter("cityName");
            String name = request.getParameter("name");
            String email = request.getParameter("email");

            CustomerDto customerDto = new CustomerDto(cityName, name, email);
            customerService.createCustomer(customerDto);

            response.sendRedirect("manageCustomer.jsp?success=true");
        } catch (Exception e) {
            System.out.println(e);
            response.sendRedirect("postCustomer.jsp?error=true");
        }
    }
}
