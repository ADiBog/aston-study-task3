package com.example.firstwebapp.dao;

import com.example.firstwebapp.entity.BeerDistributor;

import java.util.ArrayList;

public interface BeerDistributorServiceDAO {
    ArrayList<BeerDistributor> select();

    void insert(BeerDistributor beerDistributor);

    void delete(int distributor_id);
}
