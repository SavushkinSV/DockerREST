package com.example;

import com.example.db.ConnectionManagerImpl;

import com.example.db.IConnectionManager;
import com.example.util.InitSQLUtil;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        IConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
        InitSQLUtil.initSQLScheme(connectionManager);
    }
}