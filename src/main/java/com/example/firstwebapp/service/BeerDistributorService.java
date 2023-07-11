package com.example.firstwebapp.service;

import com.example.firstwebapp.business.ConnectionProvider;
import com.example.firstwebapp.dao.BeerDistributorServiceDAO;
import com.example.firstwebapp.entity.BeerDistributor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BeerDistributorService implements BeerDistributorServiceDAO {
    public BeerDistributorService(ConnectionProvider connProvider) {
        connection = connProvider.getNewConnection();
    }

    private final Connection connection;

    public ArrayList<BeerDistributor> select() {

        ArrayList<BeerDistributor> beerDistributors = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bd");
            while (resultSet.next()) {
                int beer_id = resultSet.getInt(1);
                int distributor_id = resultSet.getInt(2);
                BeerDistributor beerDistributor = new BeerDistributor(beer_id, distributor_id);
                beerDistributors.add(beerDistributor);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return beerDistributors;
    }

    @Override
    public void insert(BeerDistributor beerDistributor) {
        try {
            String sql = "INSERT INTO bd (beer_id, distributor_id) Values (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, beerDistributor.getBeer_id());
                preparedStatement.setInt(2, beerDistributor.getDistributor_id());
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void delete(int beer_id) {
        try {
            String sql = "DELETE FROM bd WHERE beer_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, beer_id);
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
