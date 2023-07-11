package com.example.firstwebapp.dao;

import com.example.firstwebapp.entity.Distributor;

import java.util.ArrayList;

public interface DistributorServiceDAO {
    ArrayList<Distributor> select();

    void insert(Distributor distributor);

    void delete(int distributor_id);
}
