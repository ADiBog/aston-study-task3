package com.example.firstwebapp.business;

import java.sql.Connection;

public interface ConnectionProvider {
    Connection getNewConnection();
}
