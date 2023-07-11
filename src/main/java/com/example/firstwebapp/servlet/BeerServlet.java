package com.example.firstwebapp.servlet;

import com.example.firstwebapp.business.JdbcConnProvider;
import com.example.firstwebapp.entity.Beer;
import com.example.firstwebapp.service.BeerService;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/beer")
public class BeerServlet extends HttpServlet {
    private final BeerService beerService;

    public BeerServlet(BeerService beerService) {
        this.beerService = beerService;
    }

    public BeerServlet() {
        beerService = new BeerService(JdbcConnProvider.getJdbcConnectionProvider());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Beer> beers = beerService.select();
        request.setAttribute("beers", beers);
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int beer_id = Integer.parseInt(request.getParameter("beer_id"));
            beerService.delete(beer_id);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } catch (Exception ex) {
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            Beer beer = new Beer(null, name);
            beerService.insert(beer);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } catch (Exception ex) {
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}


