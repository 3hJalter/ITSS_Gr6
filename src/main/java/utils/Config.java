package utils;

/**
 * The Config class provides constants that define configuration settings for various aspects of the application.
 */
public class Config {

    /**
     * The JDBC URL for connecting to the main database of the EcoBikeSystem.
     */
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/EcoBikeSystem";

    /**
     * The username for authenticating the connection to the main database.
     */
    public static final String DB_USERNAME = "postgres";

    /**
     * The password for authenticating the connection to the main database.
     */
    public static final String DB_PASSWORD = "123456";

    /**
     * The URL used by the client to access the application.
     */
    public static final String CLIENT_URL = "http://localhost:5173";

    /**
     * The JDBC URL for connecting to the Interbank database.
     */
    public static final String DB_INTERBANK_URL = "jdbc:postgresql://localhost:5432/Interbank";
}
