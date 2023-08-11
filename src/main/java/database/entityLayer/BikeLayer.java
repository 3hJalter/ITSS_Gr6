package database.entityLayer;

import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import entity.Bike;
import entity.Category;
import entity.Dock;
import entity.EBike;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BikeLayer {
    private static BikeLayer instance;

    private ResultSet resultSet;

    private BikeLayer() {
        try {
            DatabaseConnection connection = PostgresConnection.getInstance();
            String sqlQuery = "SELECT * FROM bike";
            this.resultSet = connection.query(sqlQuery);
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
        return getBikeFromResult();
    }

    public List<EBike> getEBikeList() {
        return getEBikeFromResult();
    }

    public List<Bike> getBikeByDockId(Integer id) {
        List<Bike> bikeList = new ArrayList<>();
        Dock dock = DockLayer.getInstance().getDockById(id);
        for (Bike bike : getBikeFromResult()) {
            if (!bike.getDock().equals(dock)) continue;
            bikeList.add(bike);
        }
        return bikeList;
    }

    public List<Bike> getBikeByCategoryId(Integer id) {
        List<Bike> bikeList = new ArrayList<>();
        Category category = CategoryLayer.getInstance().getCategoryById(id);
        for (Bike bike : getBikeFromResult()) {
            if (!bike.getCategory().equals(category)) continue;
            bikeList.add(bike);
        }
        return bikeList;
    }

    public Bike getBikeById(Integer id){
        for (Bike bike : getBikeFromResult()) {
            if (bike.getBikeId().equals(id)) return bike;
        }
        return null;
    }

    private List<Bike> getBikeFromResult(){
        List<Bike> bikeList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Category category = CategoryLayer.getInstance().getCategoryById(
                        resultSet.getInt("category_id")
                );
                Dock dock = DockLayer.getInstance().getDockById(
                        resultSet.getInt("dock_id")
                );
                Bike Bike = new Bike(resultSet.getInt("bike_id"),
                        resultSet.getString("bike_name"),
                        resultSet.getString("status"),
                        category,
                        dock,
                        resultSet.getString("image"));
                assert false;
                bikeList.add(Bike);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bikeList;
    }

    private List<EBike> getEBikeFromResult() {
        List<EBike> eBikeList = new ArrayList<>();
        CategoryLayer.getInstance().getCategoryList();
        try {
            while (resultSet.next()) {
                Category category = CategoryLayer.getInstance().getCategoryById(
                        resultSet.getInt("category_id")
                );
                Dock dock = DockLayer.getInstance().getDockById(
                        resultSet.getInt("dock_id")
                );
                EBike eBike = new EBike(resultSet.getInt("bike_id"),
                        resultSet.getString("bike_name"),
                        resultSet.getString("status"),
                        resultSet.getInt("battery"),
                        category,
                        dock,
                        resultSet.getString("image"));
                assert false;
                eBikeList.add(eBike);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eBikeList;
    }
}
