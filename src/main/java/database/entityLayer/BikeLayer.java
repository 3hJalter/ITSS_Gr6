package database.entityLayer;

import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
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
            String sqlQuery = "SELECT * FROM Bike";
            ResultSet resultSet = connection.query(sqlQuery);
            while (resultSet.next()) {
                Bike Bike = new Bike(resultSet.getInt("bike_id"),
                        resultSet.getString("bike_name"),
                        resultSet.getString("status"),
                        resultSet.getInt("battery"),
                        resultSet.getInt("category_id"),
                        resultSet.getInt("bike_id"),
                        resultSet.getString("image"));
                assert false;
                bikeList.add(Bike);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BikeLayer getInstance() {
        if (instance == null) {
            instance = new BikeLayer();
        }
        return instance;
    }

    public List<Bike> getBikeList() {
        return bikeList;
    }

    public List<Bike> getEBikeList() {
        List<Bike> eBikeList = new ArrayList<>();
        for (Bike bike : bikeList) {
            if (bike.getBattery() == null) continue;
            eBikeList.add(bike);
        }
        return eBikeList;
    }

    public List<Bike> getBikeByDockId(Integer id) {
        List<Bike> bikeList = new ArrayList<>();
        for (Bike bike : this.bikeList) {
            if (!bike.getDockId().equals(id)) continue;
            bikeList.add(bike);
        }
        return bikeList;
    }

    public List<Bike> getBikeByCategoryId(Integer id) {
        List<Bike> bikeList = new ArrayList<>();
        for (Bike bike : this.bikeList) {
            if (!bike.getCategoryId().equals(id)) continue;
            bikeList.add(bike);
        }
        return bikeList;
    }

    public Bike getBikeById(Integer id){
        for (Bike bike : bikeList) {
            if (bike.getBikeId().equals(id)) return bike;
        }
        return null;
    }

}
