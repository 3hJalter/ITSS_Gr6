package database.entityLayer;

import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import entity.Category;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.General;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryLayer {
    private static CategoryLayer instance;
    JSONArray jsonArray;

    private CategoryLayer() {
        try {
            DatabaseConnection connection = PostgresConnection.getInstance();
            String sqlQuery = "SELECT * FROM category";
            ResultSet resultSet = connection.query(sqlQuery);
            jsonArray = General.convertResultSetToJsonArray(resultSet);
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


    public List<Category> getCategoryList() {
        return getCategoryFromJSON();
    }

    public List<Category> search(String keyword) {
        List<Category> list = new ArrayList<>();
        for (Category category : getCategoryFromJSON()) {
            if (category.getCategoryName().contains(keyword)) list.add(category);
        }
        return list;
    }

    public Category getCategoryById(Integer id) {
        for (Category category : getCategoryFromJSON()) {
            if (category.getCategoryId().equals(id)) return category;
        }
        return null;
    }

    private List<Category> getCategoryFromJSON() {
        List<Category> categoryList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject categoryJson = jsonArray.getJSONObject(i);
                Category category = new Category(categoryJson.getInt("category_id"),
                        categoryJson.getString("category_name"),
                        categoryJson.getDouble("price"),
                        categoryJson.getDouble("price_multiple"));
                assert false;
                categoryList.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }
}
