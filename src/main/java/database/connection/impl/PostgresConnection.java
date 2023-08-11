package database.connection.impl;

import database.connection.IDatabaseConnection;

import java.sql.*;

import static utils.Config.*;

public class PostgresConnection implements IDatabaseConnection {
    private static PostgresConnection instance;
    private Connection connection;

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

    public ResultSet getData(String sqlQuery) {
        try {
            Statement statement = getConnection().createStatement();
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void insertData(String sqlQuery) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1); // Assuming the first column is an auto-generated ID
                    System.out.println("Data inserted successfully with ID: " + generatedId);
                }
            } else {
                System.out.println("Failed to get a valid database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error when inserting data: " + e.getMessage());
        }
    }

    public void closeConnection() {
        if (connection != null)
            try {
                connection.close();
                connection = null;
                System.out.println("Close connection successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
