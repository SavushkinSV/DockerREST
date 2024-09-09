package com.example;

import com.example.db.ConnectionDB;

import com.example.utils.InitSQLUtil;

public class App {
    public static void main(String[] args) {
        ConnectionDB connection = ConnectionDB.getInstance();
        InitSQLUtil.initSQL(connection.getConnection());
    }
}