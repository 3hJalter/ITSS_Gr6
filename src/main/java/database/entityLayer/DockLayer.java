package database.entityLayer;

import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import entity.Dock;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DockLayer {
    private static DockLayer instance;

    private ResultSet resultSet;

    private DockLayer() {
        try {
            DatabaseConnection connection = PostgresConnection.getInstance();
            String sqlQuery = "SELECT * FROM dock";
            resultSet = connection.query(sqlQuery);
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
        return getDockFromResult();
    }

    public List<Dock> searchDock(String keyword) {
        List<Dock> list = new ArrayList<>();
        for (Dock dock : getDockFromResult()) {
            if (dock.getDockName().contains(keyword)) list.add(dock);
        }
        return list;
    }

    public Dock getDockById(Integer id){
        for (Dock dock : getDockFromResult()) {
            if (dock.getDockId().equals(id)) return dock;
        }
        return null;
    }

    private List<Dock> getDockFromResult(){
        List<Dock> dockList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Dock dock = new Dock(resultSet.getInt("dock_id"),
                        resultSet.getString("dock_name"),
                        resultSet.getString("image"),
                        resultSet.getString("address"));
                assert false;
                dockList.add(dock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dockList;
    }
}
