import controller.DockController;
import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import entity.Dock;
import utils.response.Response;

import javax.print.Doc;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Test");
        DatabaseConnection connection = PostgresConnection.getInstance();
        connection.getConnection();
        // Check if database can init one instance
        DatabaseConnection connection1 = PostgresConnection.getInstance();
        connection1.getConnection();
        // Check if system can request Dock Read method
        DockController dockController = DockController.getInstance();
        Response<List<Dock>> response = dockController.searchDock("Dock");
        System.out.println(response.getMessage());
        List<Dock> dockList = response.getObject();
        System.out.println(dockList.get(0).getDockName());
        Response<Dock> response1 = dockController.getDockById(1);
        System.out.println(response1.getMessage());
        Dock dock = response1.getObject();
        System.out.println(dock.getAddress());
    }
}
