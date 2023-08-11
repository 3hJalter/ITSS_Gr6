package database.entityLayer.impl;

import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import database.entityLayer.IEntityLayer;
import entity.Bike;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BikeLayer {
    private static BikeLayer instance;
    private List<Bike> bikeList;

    private BikeLayer() {
        try {
            this.bikeList = new ArrayList<>();
            DatabaseConnection connection = PostgresConnection.getInstance();
            String sqlQuery = "SELECT * FROM bike";
            ResultSet resultSet = connection.query((sqlQuery));
            while (resultSet.next()) {
                Bike bike = new Bike(resultSet.getInt("bike_id"),
                        resultSet.getString("bike_name"),
                        resultSet.getString("status"),
                        resultSet.getInt("category_id"),
                        resultSet.getInt("dock_id"),
                        resultSet.getString("image"));
                assert false;
                bikeList.add(bike);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BikeLayer getInstance() {
        if (instance == null)
            instance = new BikeLayer();
        return instance;
    }

    public List<Bike> getList() {
        return bikeList;
    }

    public Bike getById(Integer id) {
        for (Bike bike : bikeList) {
            if (bike.getBikeId().equals(id))
                return bike;
        }
        return null;
    }
}
