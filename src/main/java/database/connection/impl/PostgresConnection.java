package database.connection.impl;

import database.connection.DatabaseConnection;

import java.sql.*;

import static utils.Config.*;

public class PostgresConnection implements DatabaseConnection {
    private static PostgresConnection instance;
    private Connection connection = null;

    public static PostgresConnection getInstance() {
        if (instance == null) {
            instance = new PostgresConnection(); // Create the instance only once
            System.out.println("Create new conn instance");
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        if (connection != null) return connection;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection to " + DB_URL + " successfully");
            return connection;
        } catch (Exception e) {
            System.out.println("Error when connecting to database");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ResultSet query(String sqlQuery) {
        try {
            Statement statement = getConnection().createStatement();
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() {
        if (connection != null)
            try {
                connection.close();
                System.out.println("Close connection successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
