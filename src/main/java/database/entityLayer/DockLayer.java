package database.entityLayer;

import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import entity.Dock;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DockLayer {
    private static DockLayer instance;
    private List<Dock> dockList;

    private DockLayer() {
        try {
            this.dockList = new ArrayList<>();
            DatabaseConnection connection = PostgresConnection.getInstance();
            String sqlQuery = "SELECT * FROM dock";
            ResultSet resultSet = connection.query(sqlQuery);
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
    }

    public static DockLayer getInstance() {
        if (instance == null) {
            instance = new DockLayer();
        }
        return instance;
    }

    public List<Dock> getDockList() {
        return dockList;
    }

    public List<Dock> searchDock(String keyword) {
        List<Dock> list = new ArrayList<>();
        for (Dock dock : dockList) {
            if (dock.getDockName().contains(keyword)) list.add(dock);
        }
        return list;
    }

    public Dock getDockById(Integer id){
        for (Dock dock : dockList) {
            if (dock.getDockId().equals(id)) return dock;
        }
        return null;
    }
}
