package database.connection;

import java.sql.Connection;
import java.sql.ResultSet;

public interface IDatabaseConnection {
    Connection getConnection();
    ResultSet getData(String sqlQuery);
    int insertData(String sqlQuery);
    void closeConnection();
}
