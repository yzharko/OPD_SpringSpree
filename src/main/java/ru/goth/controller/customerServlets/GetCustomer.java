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
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "getCustomer", value = "/getCustomer")
public class GetCustomer extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ru.goth.controller.customerServlets.GetCustomer.class.getName());
    private final CustomerService customerService;

    public GetCustomer() {
        this.customerService = new CustomerServiceImpl(new CustomerRepositoryImpl());
    }

    public GetCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String action = request.getParameter("action");
            String idParam = request.getParameter("id");

            if ("all".equals(action)) {
                List<CustomerDto> customers = customerService.getAllCustomers();
                request.setAttribute("customers", customers);
                request.getRequestDispatcher("/getCustomer.jsp").forward(request, response);
            } else if (idParam != null && !idParam.isEmpty()) {
                long id = Long.parseLong(idParam);
                CustomerDto customer = customerService.getCustomerById(id);

                if (customer != null) {
                    request.setAttribute("customers", List.of(customer));
                    request.getRequestDispatcher("/getCustomer.jsp").forward(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Customer not found\"}");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Missing parameters\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid ID format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Server error\"}");
            e.printStackTrace();
        }
    }
}
