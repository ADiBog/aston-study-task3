package com.example.firstwebapp.service;

import com.example.firstwebapp.business.ConnectionProvider;
import com.example.firstwebapp.dao.DistributorServiceDAO;
import com.example.firstwebapp.entity.Distributor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DistributorService implements DistributorServiceDAO {
    public DistributorService(ConnectionProvider connectionProvider) {
        connection = connectionProvider.getNewConnection();
    }

    private final Connection connection;

    public ArrayList<Distributor> select() {

        ArrayList<Distributor> distributors = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM distributor");
            while (resultSet.next()) {
                int distributor_id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Distributor distributor = new Distributor(distributor_id, name);
                distributors.add(distributor);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return distributors;
    }
    public void insert(Distributor distributor) {
        try {
            String sql = "INSERT INTO distributor (name) Values (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, distributor.getName());
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void delete(int distributor_id) {
        try {
            String sql = "DELETE FROM distributor WHERE distributor_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, distributor_id);
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
