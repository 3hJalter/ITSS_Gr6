import controller.BikeController;
import controller.DockController;
import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import entity.Bike;
import entity.Dock;
import entity.EBike;
import utils.response.Response;
import utils.response.responseMessageImpl.BikeResponseMessage;
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
        for (Dock dock : dockList) {
            System.out.println(dock.convertToString());
        }
        System.out.println("--------------");
        Response<Dock> response1 = dockController.getDockById(1);
        String message = response1.getMessage();
        if (message.equals(DockResponseMessage.SUCCESSFUL.getMessage())) {
            Dock dock = response1.getObject();
            System.out.println(dock.convertToString());
        } else System.out.println(message);
        System.out.println("--------------");
        BikeController bikeController = BikeController.getInstance();
        Response<List<Bike>> response2 = bikeController.getBikeList();
        if (response2.getMessage().equals(BikeResponseMessage.SUCCESSFUL.getMessage())) {
            List<Bike> bikeList = response2.getObject();
            for (Bike bike : bikeList) {
                System.out.println(bike.convertToString());
            }
        }
        System.out.println("--------------");
        Response<List<EBike>> response3 = bikeController.getEBikeList();
        if (response3.getMessage().equals(BikeResponseMessage.SUCCESSFUL.getMessage())) {
            List<EBike> bikeList = response3.getObject();
            for (EBike bike : bikeList) {
                System.out.println(bike.convertToString());
            }
        }
        System.out.println("--------------");
        Response<List<Bike>> response4 = bikeController.getBikeByDockId(1);
        if (response4.getMessage().equals(BikeResponseMessage.SUCCESSFUL.getMessage())) {
            List<Bike> bikeList = response4.getObject();
            for (Bike bike : bikeList) {
                System.out.println(bike.convertToString());
            }
        }
        System.out.println("--------------");
        Response<List<Bike>> response5 = bikeController.getBikeByCategoryId(1);
        if (response5.getMessage().equals(BikeResponseMessage.SUCCESSFUL.getMessage())) {
            List<Bike> bikeList = response5.getObject();
            for (Bike bike : bikeList) {
                System.out.println(bike.convertToString());
            }
        }
    }
}
