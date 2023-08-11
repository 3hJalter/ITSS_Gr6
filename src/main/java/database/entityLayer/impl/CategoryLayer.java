package database.entityLayer.impl;

import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import database.entityLayer.IEntityLayer;
import entity.Category;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryLayer implements IEntityLayer {
    private static CategoryLayer instance;
    private List<Category> categoryList;

    private CategoryLayer() {
        try {
            DatabaseConnection connection = PostgresConnection.getInstance();
            String sqlQuery = "SELECT * FROM category";
            ResultSet resultSet = connection.query(sqlQuery);
            while (resultSet.next()) {
                Category category = new Category(resultSet.getInt("category_id"),
                        resultSet.getString("category_name"),
                        resultSet.getDouble("price"),
                        resultSet.getFloat("price_multiple"));
                assert false;
                categoryList.add(category);
            }
            this.categoryList = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CategoryLayer getInstance() {
        if (instance == null) {
            instance = new CategoryLayer();
        }
        return instance;
    }


    public List<Category> getList() {
        return categoryList;
    }

    public List<Category> search(String keyword) {
        List<Category> list = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.getCategoryName().contains(keyword)) list.add(category);
        }
        return list;
    }
}
