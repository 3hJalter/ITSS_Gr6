import controller.DockController;
import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import entity.Dock;
import utils.response.Response;
import utils.response.responseMessageImpl.DockResponseMessage;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Test");
        DatabaseConnection connection = PostgresConnection.getInstance();
        connection.getConnection();
        // Check if database can init one instance
        DatabaseConnection connection1 = PostgresConnection.getInstance();
        connection1.getConnection();
        connection1.closeConnection();
        System.out.println("--------------");
        // Check if system can request Dock Read method
        DockController dockController = DockController.getInstance();
        Response<List<Dock>> response = dockController.searchDock("Dock");
        System.out.println(response.getMessage());
        List<Dock> dockList = response.getObject();
        System.out.println(dockList.get(0).getDockName());
        Response<Dock> response1 = dockController.getDockById(1);
        String message = response1.getMessage();
        if (message.equals(DockResponseMessage.SUCCESSFUL.getMessage())) {
            Dock dock = response1.getObject();
            System.out.println(dock.getDockId());
            System.out.println(dock.getDockName());
            System.out.println(dock.getAddress());
        } else System.out.println(message);
    }
}
