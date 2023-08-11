package database.entityLayer;

import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import entity.Bike;
import entity.Category;
import entity.Dock;
import entity.EBike;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.General;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BikeLayer {
    private static BikeLayer instance;

    private JSONArray jsonArray;

    private BikeLayer() {
        try {
            DatabaseConnection connection = PostgresConnection.getInstance();
            String sqlQuery = "SELECT * FROM bike";
            ResultSet resultSet = connection.query(sqlQuery);
            jsonArray = General.convertResultSetToJsonArray(resultSet);
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
        return getEBikeFromJSON();
    }

    public List<Bike> getBikeByDockId(Integer id) {
        List<Bike> bikeList = new ArrayList<>();
        if (id == null) return bikeList;
        for (Bike bike : getBikeFromResult()) {
            if (bike.getDock() == null) continue;
            if (!bike.getDock().getDockId().equals(id)) continue;
            bikeList.add(bike);
        }
        return bikeList;
    }

    public List<Bike> getBikeByCategoryId(Integer id) {
        List<Bike> bikeList = new ArrayList<>();
        if (id == null) return bikeList;
        for (Bike bike : getBikeFromResult()) {
            if (bike.getCategory() == null) continue;
//            if (bike.getDock() == null) continue;
            if (!bike.getCategory().getCategoryId().equals(id)) continue;
            bikeList.add(bike);
        }
        return bikeList;
    }

    public Bike getBikeById(Integer id){
        if (id == null) return null;
        for (Bike bike : getBikeFromResult()) {
            if (bike.getBikeId().equals(id)) return bike;
        }
        return null;
    }

    private List<Bike> getBikeFromResult(){
        List<Bike> bikeList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bikeJson = jsonArray.getJSONObject(i);
                Category category = CategoryLayer.getInstance().getCategoryById(
                        bikeJson.getInt("category_id")
                );

                Dock dock = null;
                if (bikeJson.has("dock_id")) {
                    dock = DockLayer.getInstance().getDockById(
                            bikeJson.getInt("dock_id")
                    );
                }

                Bike bike = new Bike(bikeJson.getInt("bike_id"),
                        bikeJson.getString("bike_name"),
                        bikeJson.getString("status"),
                        category,
                        dock,
                        bikeJson.getString("image"));
                assert false;
                bikeList.add(bike);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bikeList;
    }

    private List<EBike> getEBikeFromJSON() {
        List<EBike> eBikeList = new ArrayList<>();
        CategoryLayer.getInstance().getCategoryList();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bikeJson = jsonArray.getJSONObject(i);
                if (!bikeJson.has("battery")) continue;
                Category category = CategoryLayer.getInstance().getCategoryById(
                        bikeJson.getInt("category_id")
                );

                Dock dock = null;
                if (bikeJson.has("dock_id")) {
                    dock = DockLayer.getInstance().getDockById(
                            bikeJson.getInt("dock_id")
                    );
                }

                EBike eBike = new EBike(bikeJson.getInt("bike_id"),
                        bikeJson.getString("bike_name"),
                        bikeJson.getString("status"),
                        bikeJson.getInt("battery"),
                        category,
                        dock,
                        bikeJson.getString("image"));
                assert false;
                eBikeList.add(eBike);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eBikeList;
    }
}
