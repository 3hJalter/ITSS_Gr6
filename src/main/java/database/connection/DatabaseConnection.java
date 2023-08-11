package database.connection;

import java.sql.Connection;
import java.sql.ResultSet;

public interface DatabaseConnection {
    Connection getConnection();
    ResultSet query(String sqlQuery);
    void closeConnection();
}
