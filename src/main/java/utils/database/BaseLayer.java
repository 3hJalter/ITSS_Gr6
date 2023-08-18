package utils.database;


import utils.database.connection.IDatabaseConnection;
import utils.database.connection.impl.PostgresConnection;

public class BaseLayer {
    protected static IDatabaseConnection databaseConnection;
    public BaseLayer(){
        if (databaseConnection == null)
            databaseConnection = PostgresConnection.getInstance();
    }
}
