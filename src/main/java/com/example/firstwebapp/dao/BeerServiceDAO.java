package com.example.firstwebapp.dao;

import com.example.firstwebapp.entity.Beer;

import java.util.ArrayList;

public interface BeerServiceDAO {
    ArrayList<Beer> select();

    void insert(Beer beer);

    void delete(int beer_id);
}
