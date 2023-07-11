package com.example.firstwebapp.servlet;

import com.example.firstwebapp.business.JdbcConnProvider;
import com.example.firstwebapp.entity.Distributor;
import com.example.firstwebapp.service.DistributorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/distributor")
public class DistributorServlet extends HttpServlet {
    private final DistributorService distributorService;

    public DistributorServlet(DistributorService distributorService) {
        this.distributorService = distributorService;
    }

    public DistributorServlet() {
        distributorService = new DistributorService(JdbcConnProvider.getJdbcConnectionProvider());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DistributorService distributorService = new DistributorService(JdbcConnProvider.getJdbcConnectionProvider());
        ArrayList<Distributor> distributors = distributorService.select();
        request.setAttribute("distributors", distributors);

        getServletContext().getRequestDispatcher("/indexDistributor.jsp").forward(request, response);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int distributor_id = Integer.parseInt(request.getParameter("distributor_id"));
            DistributorService distributorService = new DistributorService(JdbcConnProvider.getJdbcConnectionProvider());
            distributorService.delete(distributor_id);
            response.sendRedirect(request.getContextPath() + "/indexDistributor.jsp");
        } catch (Exception ex) {
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            Distributor distributor = new Distributor(null, name);
            DistributorService distributorService = new DistributorService(JdbcConnProvider.getJdbcConnectionProvider());
            distributorService.insert(distributor);
            response.sendRedirect(request.getContextPath() + "/indexDistributor.jsp");
        } catch (Exception ex) {
            getServletContext().getRequestDispatcher("/indexDistributor.jsp").forward(request, response);
        }
    }
}


