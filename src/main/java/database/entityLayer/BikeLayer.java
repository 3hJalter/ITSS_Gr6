package database.entityLayer;

import entity.Category;
import entity.Dock;
import entity.Invoice;
import entity.bike.Bike;
import entity.bike.EBike;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.General;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BikeLayer extends BaseLayer {
    private static BikeLayer instance;

    private JSONArray jsonArray;

    private BikeLayer() {
        setJsonArray();
    }

    public static BikeLayer getInstance() {
        if (instance == null) {
            instance = new BikeLayer();
        }
        return instance;
    }

    private void setJsonArray() {
        try {
            String sqlQuery = "SELECT * FROM bike";
            ResultSet resultSet = databaseConnection.getData(sqlQuery);
            jsonArray = General.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
    }

    public List<Bike> getBikeList() {
        return getBikeFromJSON();
    }

    public List<EBike> getEBikeList() {
        return getEBikeFromJSON();
    }

    public List<Bike> getBikeByDockId(Integer id) {
        List<Bike> bikeList = new ArrayList<>();
        if (id == null) return bikeList;
        for (Bike bike : getBikeFromJSON()) {
            if (bike.getDock() == null) continue;
            if (!bike.getDock().getDockId().equals(id)) continue;
            bikeList.add(bike);
        }
        return bikeList;
    }

    public List<Bike> getBikeByCategoryId(Integer id) {
        List<Bike> bikeList = new ArrayList<>();
        if (id == null) return bikeList;
        for (Bike bike : getBikeFromJSON()) {
            if (bike.getCategory() == null) continue;
//            if (bike.getDock() == null) continue;
            if (!bike.getCategory().getCategoryId().equals(id)) continue;
            bikeList.add(bike);
        }
        return bikeList;
    }

    public Bike getBikeById(Integer id) {
        if (id == null) return null;
        for (Bike bike : getBikeFromJSON()) {
            if (bike.getBikeId().equals(id)) return bike;
        }
        return null;
    }

    private List<Bike> getBikeFromJSON() {
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

                Bike bike;
                if (!bikeJson.has("battery")) {
                    bike = new Bike(bikeJson.getInt("bike_id"),
                            bikeJson.getString("bike_name"),
                            category,
                            dock,
                            bikeJson.getString("image"));
                } else {
                    Integer battery = bikeJson.getInt("battery");
                    bike = new EBike(bikeJson.getInt("bike_id"),
                            bikeJson.getString("bike_name"),
                            battery,
                            category,
                            dock,
                            bikeJson.getString("image"));
                }
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
                        bikeJson.getInt("battery"),
                        category,
                        dock,
                        bikeJson.getString("image"));
                
                eBikeList.add(eBike);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eBikeList;
    }

    public void rentBikeById(Integer bikeId) {
        try {
            databaseConnection.getConnection().setAutoCommit(false);
            String sqlQuery = "UPDATE bike \n"
                    + "SET dock_id = null \n"
                    + "WHERE bike_id = " + bikeId
                    + ";";
            databaseConnection.updateData(sqlQuery);
            databaseConnection.getConnection().commit();
            databaseConnection.getConnection().setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            setJsonArray();
        }
    }

    public void returnInvoiceBikeToDockId(Integer invoiceId, Integer dockId) {
        Invoice invoice = InvoiceLayer.getInstance().getInvoiceById(invoiceId);
        if (invoice == null) return;
        Dock dock = DockLayer.getInstance().getDockById(dockId);
        if (dock == null) return;
        try {
            databaseConnection.getConnection().setAutoCommit(false);
            String sqlQuery = "UPDATE bike \n"
                    + "SET dock_id = " + dockId + " \n"
                    + "WHERE bike_id = " + invoice.getBike().getBikeId()
                    + ";";
            databaseConnection.updateData(sqlQuery);
            databaseConnection.getConnection().commit();
            databaseConnection.getConnection().setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            setJsonArray();
        }
    }
}
