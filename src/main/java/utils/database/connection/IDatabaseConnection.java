package utils.database.connection;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * The IDatabaseConnection interface defines a contract for managing database connections
 * and performing common database operations.
 */
public interface IDatabaseConnection {

    /**
     * Retrieves a connection to the database.
     *
     * @return A Connection object representing the database connection.
     */
    Connection getConnection();

    /**
     * Executes a SQL query and retrieves the resulting data as a ResultSet.
     *
     * @param sqlQuery The SQL query to execute.
     * @return A ResultSet containing the retrieved data.
     */
    ResultSet getData(String sqlQuery);

    /**
     * Executes an SQL query to insert data into the database.
     *
     * @param sqlQuery The SQL query to execute for insertion.
     * @return The number of rows affected by the insertion.
     */
    int insertData(String sqlQuery);

    /**
     * Executes an SQL query to update data in the database.
     *
     * @param sqlQuery The SQL query to execute for updating.
     */
    void updateData(String sqlQuery);

    /**
     * Closes the database connection, releasing associated resources.
     */
    void closeConnection();
}
