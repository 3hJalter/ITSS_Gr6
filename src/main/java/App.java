import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;

public class App {
    public static void main(String[] args) {
        System.out.println("Test");
        DatabaseConnection connection = PostgresConnection.getInstance();
        connection.getConnection();
        //connection.closeConnection();
        DatabaseConnection connection1 = PostgresConnection.getInstance();
        connection1.getConnection();
    }
}
