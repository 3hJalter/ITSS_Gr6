package database.entityLayer;

import database.connection.DatabaseConnection;
import database.connection.impl.PostgresConnection;
import entity.Category;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryLayer {
    private static CategoryLayer instance;
    
    private ResultSet resultSet;

    private CategoryLayer() {
        try {
            DatabaseConnection connection = PostgresConnection.getInstance();
            String sqlQuery = "SELECT * FROM category";
            resultSet = connection.query(sqlQuery);
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
        return getCategoryFromResult();
    }

    public List<Category> search(String keyword) {
        List<Category> list = new ArrayList<>();
        for (Category category : getCategoryFromResult()) {
            if (category.getCategoryName().contains(keyword)) list.add(category);
        }
        return list;
    }

    public Category getCategoryById(Integer id){
        for (Category category : getCategoryFromResult()) {
            if (category.getCategoryId().equals(id)) return category;
        }
        return null;
    }

    private List<Category> getCategoryFromResult(){
        List<Category> categoryList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Category category = new Category(resultSet.getInt("category_id"),
                        resultSet.getString("category_name"),
                        resultSet.getDouble("price"),
                        resultSet.getFloat("price_multiple"));
                assert false;
                categoryList.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }
}
