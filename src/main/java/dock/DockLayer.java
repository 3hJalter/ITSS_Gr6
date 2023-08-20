package dock;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.JsonFunction;
import utils.database.BaseLayer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * The DockLayer class provides access to dock data stored in a JSON format,
 * retrieved from a database table. It extends the BaseLayer class for database connection management.
 */
public class DockLayer extends BaseLayer {
    private static DockLayer instance;
    JSONArray jsonArray;

    /**
     * Private constructor that initializes the DockLayer instance by fetching dock data
     * from the database and converting it to JSON.
     */
    private DockLayer() {
        try {
            String sqlQuery = "SELECT * FROM dock ORDER BY dock_id ASC";
            ResultSet resultSet = databaseConnection.getData(sqlQuery);
            jsonArray = JsonFunction.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
    }

    /**
     * Get the singleton instance of the DockLayer class.
     *
     * @return The instance of the DockLayer.
     */
    public static DockLayer getInstance() {
        if (instance == null) {
            instance = new DockLayer();
        }
        return instance;
    }

    /**
     * Get a list of all docks stored in the JSON data.
     *
     * @return A list of Dock objects representing the docks.
     */
    public List<Dock> getDockList() {
        return getDockFromJSON();
    }

    /**
     * Search for docks by a keyword in their names.
     *
     * @param keyword The keyword to search for in dock names.
     * @return A list of Dock objects matching the search keyword.
     */
    public List<Dock> searchDock(String keyword) {
        List<Dock> list = new ArrayList<>();
        for (Dock dock : getDockFromJSON()) {
            if (dock.getDockName().contains(keyword)) list.add(dock);
        }
        return list;
    }

    /**
     * Get a dock by its unique identifier.
     *
     * @param id The ID of the dock to retrieve.
     * @return The Dock object representing the dock with the given ID,
     *         or null if no dock with the provided ID is found.
     */
    public Dock getDockById(Integer id) {
        if (id == null) return null;
        for (Dock dock : getDockFromJSON()) {
            if (dock.getDockId().equals(id)) return dock;
        }
        return null;
    }

    /**
     * Extract a list of Dock objects from the stored JSON data.
     *
     * @return A list of Dock objects parsed from the JSON data.
     */
    private List<Dock> getDockFromJSON() {
        List<Dock> dockList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject dockJson = jsonArray.getJSONObject(i);
                Dock dock = new Dock(
                        dockJson.getInt("dock_id"),
                        dockJson.getString("dock_name"),
                        dockJson.getString("image"),
                        dockJson.getString("address")
                );
                dockList.add(dock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dockList;
    }
}
