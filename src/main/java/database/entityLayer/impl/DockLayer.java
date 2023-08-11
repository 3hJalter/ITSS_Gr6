package database.entityLayer.impl;

import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import database.entityLayer.IEntityLayer;
import entity.Dock;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DockLayer implements IEntityLayer {
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

    public List<Dock> getList() {
        return dockList;
    }

    public List<Dock> searchByName(String keyword) {
        List<Dock> list = new ArrayList<>();
        for (Dock dock : dockList) {
            if (dock.getDockName().contains(keyword))
                list.add(dock);
        }
        return list;
    }

    public List<Dock> searchByAddress(String keyword) {
        List<Dock> list = new ArrayList<>();
        for (Dock dock : dockList) {
            if (dock.getAddress().contains(keyword))
                list.add(dock);
        }
        return list;
    }

    public Dock getById(Integer id) {
        for (Dock dock : dockList) {
            if (dock.getDockId().equals(id))
                return dock;
        }
        return null;
    }
}
