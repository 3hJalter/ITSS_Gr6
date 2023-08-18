package utils.database.connection.impl;

import utils.database.connection.IDatabaseConnection;

import java.sql.*;

import static utils.Config.*;

/**
 * The PostgresConnection class implements the IDatabaseConnection interface
 * and provides methods for managing database connections and executing queries
 * on a PostgresSQL database.
 */
public class PostgresConnection implements IDatabaseConnection {
    private static PostgresConnection instance;
    private Connection connection;

    /**
     * Retrieves an instance of the PostgresConnection class.
     *
     * @return An instance of PostgresConnection.
     */
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
            return connection;
        } catch (Exception e) {
            System.out.println("Error when connecting to the database");
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
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
    public int insertData(String sqlQuery) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1); // Assuming the first column is an auto-generated ID
                    System.out.println("Data inserted successfully with ID: " + generatedId);
                    return generatedId;
                }
            }
            System.out.println("Failed to get a valid database connection.");
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error when inserting data: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public void updateData(String sqlQuery) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println("Data updated successfully. Rows affected: " + rowsAffected);
                return;
            }
            System.out.println("Failed to get a valid database connection.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error when updating data: " + e.getMessage());
        }
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
