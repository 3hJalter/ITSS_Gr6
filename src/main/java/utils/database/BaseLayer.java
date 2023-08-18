package utils.database;

import utils.database.connection.IDatabaseConnection;
import utils.database.connection.impl.PostgresConnection;

/**
 * The BaseLayer class serves as the foundational layer for database operations,
 * providing a shared database connection across derived classes.
 */
public class BaseLayer {

    /**
     * The shared instance of the database connection.
     */
    protected static IDatabaseConnection databaseConnection;

    /**
     * Constructs a BaseLayer object and initializes the database connection
     * if it hasn't been initialized already.
     */
    public BaseLayer() {
        if (databaseConnection == null) {
            // Initialize the database connection using the PostgresConnection implementation.
            databaseConnection = PostgresConnection.getInstance();
        }
    }
}
