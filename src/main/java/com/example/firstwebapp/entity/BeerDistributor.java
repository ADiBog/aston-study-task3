package com.example.firstwebapp.entity;

import java.io.Serializable;

public class BeerDistributor implements Serializable {
    private static final long serialVersionUID = 1L;
    Integer beer_id;
    Integer distributor_id;

    public BeerDistributor(Integer beer_id, Integer distributor_id) {
        this.beer_id = beer_id;
        this.distributor_id = distributor_id;
    }

    public Integer getBeer_id() {
        return beer_id;
    }

    public Integer getDistributor_id() {
        return distributor_id;
    }
}