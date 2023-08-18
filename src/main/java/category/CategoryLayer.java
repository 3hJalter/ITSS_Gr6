package category;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.JsonFunction;
import utils.database.BaseLayer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * The CategoryLayer class interacts with the database and provides methods to retrieve and manage category data.
 */
public class CategoryLayer extends BaseLayer {
    private static CategoryLayer instance;
    JSONArray jsonArray;

    /**
     * Constructs a new CategoryLayer instance by retrieving category data from the database.
     * Private constructor to enforce the singleton pattern.
     */
    private CategoryLayer() {
        try {
            String sqlQuery = "SELECT * FROM category";
            ResultSet resultSet = databaseConnection.getData(sqlQuery);
            jsonArray = JsonFunction.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
    }

    /**
     * Get the singleton instance of the CategoryLayer class.
     *
     * @return The singleton instance of CategoryLayer.
     */
    public static CategoryLayer getInstance() {
        if (instance == null) {
            instance = new CategoryLayer();
        }
        return instance;
    }

    /**
     * Retrieve a list of Category objects.
     *
     * @return A list of Category objects.
     */
    public List<Category> getCategoryList() {
        return getCategoryFromJSON();
    }

    /**
     * Retrieve a Category object by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return The Category object with the specified ID, or null if not found.
     */
    public Category getCategoryById(Integer id) {
        if (id == null) return null;
        for (Category category : getCategoryFromJSON()) {
            if (category.getCategoryId().equals(id)) return category;
        }
        return null;
    }

    /**
     * Convert the JSON data to a list of Category objects.
     *
     * @return A list of Category objects.
     */
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

                categoryList.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }
}
