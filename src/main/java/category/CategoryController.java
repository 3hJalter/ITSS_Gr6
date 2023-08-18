package category;

import utils.response.Response;
import utils.response.responseMessageImpl.CategoryResponseMessage;

import java.util.List;

public class CategoryController {
    private static CategoryController instance;
    private static CategoryLayer categoryLayer;

    CategoryController() {
        categoryLayer = CategoryLayer.getInstance();
    }

    public static CategoryController getInstance() {
        if (instance == null) {
            instance = new CategoryController();
        }
        return instance;
    }

    public Response<List<Category>> getCategoryList() {
        List<Category> categoryList = categoryLayer.getCategoryList();
        return new Response<>(categoryList, CategoryResponseMessage.SUCCESSFUL);
    }
}
