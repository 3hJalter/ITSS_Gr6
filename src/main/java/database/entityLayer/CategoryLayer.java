package database.entityLayer;

import entity.Category;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.General;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryLayer extends BaseLayer {
    private static CategoryLayer instance;
    JSONArray jsonArray;

    private CategoryLayer() {
        try {
            String sqlQuery = "SELECT * FROM category";
            ResultSet resultSet = databaseConnection.getData(sqlQuery);
            jsonArray = General.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
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

    public Category getCategoryById(Integer id) {
        if (id == null) return null;
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
                        categoryJson.getLong("bike_price"),
                        categoryJson.getDouble("deposit_rate"),
                        categoryJson.getLong("rent_price"),
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
