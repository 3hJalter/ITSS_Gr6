package bike;

import bike.bikeEntity.Bike;
import bike.bikeEntity.EBike;
import category.Category;
import category.CategoryLayer;
import dock.Dock;
import dock.DockLayer;
import invoice.Invoice;
import invoice.InvoiceLayer;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JsonFunction;
import utils.database.BaseLayer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The BikeLayer class provides methods for managing and interacting with bike-related data.
 * This class handles operations such as retrieving bike information, renting bikes, and returning bikes to docks.
 */
public class BikeLayer extends BaseLayer {
    private static BikeLayer instance;

    private JSONArray jsonArray;

    /**
     * Private constructor that initializes the BikeLayer instance by fetching bike data
     * from the database and converting it to JSON.
     */
    private BikeLayer() {
        setJsonArray();
    }

    /**
     * Get the singleton instance of BikeLayer.
     *
     * @return The instance of BikeLayer.
     */
    public static BikeLayer getInstance() {
        if (instance == null) {
            instance = new BikeLayer();
        }
        return instance;
    }

    /**
     * Sets the JSON array with credit card data retrieved from the database.
     */
    private void setJsonArray() {
        try {
            String sqlQuery = "SELECT * FROM bike";
            ResultSet resultSet = databaseConnection.getData(sqlQuery);
            jsonArray = JsonFunction.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
    }

    /**
     * Retrieve a list of all bikes stored in the system.
     *
     * @return A list of Bike objects representing all bikes.
     */
    public List<Bike> getBikeList() {
        return getBikeFromJSON();
    }

    /**
     * Retrieve a list of all e-bikes stored in the system.
     *
     * @return A list of EBike objects representing all e-bikes.
     */
    public List<EBike> getEBikeList() {
        return getEBikeFromJSON();
    }

    /**
     * Retrieve a bike based on its barcode.
     *
     * @param barcode The UUID barcode of the bike.
     * @return The Bike object corresponding to the given barcode, or null if not found.
     */
    public Bike getBikeByBarcode(UUID barcode) {
        if (barcode == null) return null;
        for (Bike bike : getBikeFromJSON()) {
            if (bike.getBarcode().equals(barcode)) return bike;
        }
        return null;
    }

    /**
     * Retrieve a list of bikes currently associated with a specific dock.
     *
     * @param id The ID of the dock.
     * @return A list of Bike objects currently associated with the specified dock.
     */
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

    /**
     * Retrieve a list of bikes based on their category ID.
     *
     * @param id The ID of the category.
     * @return A list of Bike objects belonging to the specified category.
     */
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

    /**
     * Retrieve a bike based on its ID.
     *
     * @param id The ID of the bike.
     * @return The Bike object corresponding to the given ID, or null if not found.
     */
    public Bike getBikeById(Integer id) {
        if (id == null) return null;
        for (Bike bike : getBikeFromJSON()) {
            if (bike.getBikeId().equals(id)) return bike;
        }
        return null;
    }

    /**
     * Extract a list of Bike objects from the stored JSON data.
     *
     * @return A list of Bike objects parsed from the JSON data.
     */
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
                UUID barcode;
                barcode = UUID.fromString(bikeJson.getString("barcode"));
                Bike bike;
                if (!bikeJson.has("battery")) {
                    bike = new Bike(bikeJson.getInt("bike_id"),
                            bikeJson.getString("bike_name"),
                            category,
                            dock,
                            bikeJson.getString("image"),
                            barcode);
                } else {
                    Integer battery = bikeJson.getInt("battery");
                    bike = new EBike(bikeJson.getInt("bike_id"),
                            bikeJson.getString("bike_name"),
                            category,
                            dock,
                            bikeJson.getString("image"),
                            barcode,
                            battery);
                }
                bikeList.add(bike);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bikeList;
    }

    /**
     * Extract a list of EBike objects from the stored JSON data.
     *
     * @return A list of Ebike objects parsed from the JSON data.
     */
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

                UUID barcode;
                barcode = UUID.fromString(bikeJson.getString("barcode"));

                EBike eBike = new EBike(bikeJson.getInt("bike_id"),
                        bikeJson.getString("bike_name"),
                        category,
                        dock,
                        bikeJson.getString("image"),
                        barcode,
                        bikeJson.getInt("battery"));

                eBikeList.add(eBike);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eBikeList;
    }

    /**
     * Rent a bike by setting its dock ID to null.
     *
     * @param bikeId The ID of the bike to be rented.
     */
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

    /**
     * Return a bike associated with an invoice to a specified dock.
     *
     * @param invoiceId The ID of the invoice.
     * @param dockId    The ID of the dock to which the bike is returned.
     */
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
