package com.example.firstwebapp.servlet;

import com.example.firstwebapp.business.JdbcConnProvider;
import com.example.firstwebapp.entity.BeerDistributor;
import com.example.firstwebapp.service.BeerDistributorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/bd")
public class BeerDistributorServlet extends HttpServlet {
    private final BeerDistributorService beerDistributorService;

    public BeerDistributorServlet(BeerDistributorService beerDistributorService) {
        this.beerDistributorService = beerDistributorService;
    }

    public BeerDistributorServlet() {
        beerDistributorService = new BeerDistributorService(JdbcConnProvider.getJdbcConnectionProvider());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<BeerDistributor> beerDistributors = beerDistributorService.select();
        request.setAttribute("beerDistributors", beerDistributors);

        getServletContext().getRequestDispatcher("/indexBD.jsp").forward(request, response);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int beer_id = Integer.parseInt(request.getParameter("beer_id"));
            beerDistributorService.delete(beer_id);
            response.sendRedirect(request.getContextPath() + "/indexBD.jsp");
        } catch (Exception ex) {
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Integer beer_id = Integer.parseInt(request.getParameter("beer_id"));
            Integer distributor_id = Integer.parseInt(request.getParameter("distributor_id"));
            BeerDistributor beerDistributor = new BeerDistributor(beer_id, distributor_id);
            beerDistributorService.insert(beerDistributor);
            response.sendRedirect(request.getContextPath() + "/indexBD.jsp");
        } catch (Exception ex) {
            getServletContext().getRequestDispatcher("/indexBD.jsp").forward(request, response);
        }
    }
}


