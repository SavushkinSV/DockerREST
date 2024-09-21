package com.example;

import com.example.db.ConnectionManagerImpl;

import com.example.db.IConnectionManager;
import com.example.util.InitSQLUtil;

public class App {
    public static void main(String[] args) {
        IConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
        InitSQLUtil.initSQLScheme(connectionManager);
    }
}