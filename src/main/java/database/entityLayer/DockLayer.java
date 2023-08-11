package database.entityLayer;

import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import entity.Dock;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.General;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DockLayer {
    private static DockLayer instance;
    JSONArray jsonArray;

    private DockLayer() {
        try {
            DatabaseConnection connection = PostgresConnection.getInstance();
            String sqlQuery = "SELECT * FROM dock";
            ResultSet resultSet = connection.query(sqlQuery);
            jsonArray = General.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DockLayer getInstance() {
        if (instance == null) {
            instance = new DockLayer();
        }
        return instance;
    }

    public List<Dock> getDockList() {
        return getDockFromJSON();
    }

    public List<Dock> searchDock(String keyword) {
        List<Dock> list = new ArrayList<>();
        for (Dock dock : getDockFromJSON()) {
            if (dock.getDockName().contains(keyword)) list.add(dock);
        }
        return list;
    }

    public Dock getDockById(Integer id){
        for (Dock dock : getDockFromJSON()) {
            if (dock.getDockId().equals(id)) return dock;
        }
        return null;
    }

    private List<Dock> getDockFromJSON(){
        List<Dock> dockList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject dockJson = jsonArray.getJSONObject(i);
                Dock dock = new Dock(dockJson.getInt("dock_id"),
                        dockJson.getString("dock_name"),
                        dockJson.getString("image"),
                        dockJson.getString("address"));
                assert false;
                dockList.add(dock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dockList;
    }
}
