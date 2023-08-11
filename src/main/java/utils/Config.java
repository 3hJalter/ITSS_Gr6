package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Config {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/EcoBikeSystem";
    public static final String DB_USERNAME = "postgres";
    public static final String DB_PASSWORD = "123456";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final Integer CUSTOMER_ID = 1;

}
