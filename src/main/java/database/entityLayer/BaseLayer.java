package database.entityLayer;


import database.connection.IDatabaseConnection;
import database.connection.impl.PostgresConnection;
public class BaseLayer {
    protected static IDatabaseConnection databaseConnection;
    BaseLayer(){
        if (databaseConnection == null)
            databaseConnection = PostgresConnection.getInstance();
    }
}
