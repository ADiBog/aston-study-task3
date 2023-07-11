package com.example.firstwebapp.service;

import com.example.firstwebapp.business.ConnectionProvider;
import com.example.firstwebapp.entity.Beer;
import com.example.firstwebapp.dao.BeerServiceDAO;

import java.sql.*;
import java.util.ArrayList;

public class BeerService implements BeerServiceDAO {
    public BeerService(ConnectionProvider connProvider) {
        connection = connProvider.getNewConnection();
    }

    private final Connection connection;

    public ArrayList<Beer> select() {

        ArrayList<Beer> beers = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM beer");
            while (resultSet.next()) {
                int beer_id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Beer beer = new Beer(beer_id, name);
                beers.add(beer);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return beers;
    }

    public void insert(Beer beer) {
        try {
            String sql = "INSERT INTO beer (name) Values (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, beer.getName());
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void delete(int beer_id) {
        try {
            String sql = "DELETE FROM beer WHERE beer_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, beer_id);
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
