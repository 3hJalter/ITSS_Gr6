package category;

import utils.response.Response;
import utils.response.responseMessageImpl.CategoryResponseMessage;

import java.util.List;

/**
 * The CategoryController class manages interactions between the user interface and the underlying
 * data and logic related to categories of bikes.
 */
public class CategoryController {
    private static CategoryController instance;
    private static CategoryLayer categoryLayer;

    /**
     * Constructs a new CategoryController instance and initializes the categoryLayer.
     */
    CategoryController() {
        categoryLayer = CategoryLayer.getInstance();
    }

    /**
     * Returns the singleton instance of the CategoryController.
     *
     * @return The singleton instance of the CategoryController.
     */
    public static CategoryController getInstance() {
        if (instance == null) {
            instance = new CategoryController();
        }
        return instance;
    }

    /**
     * Retrieves a list of categories of bikes.
     *
     * @return A Response containing a list of Category objects and a success message,
     *         or an appropriate error message if the operation fails.
     */
    public Response<List<Category>> getCategoryList() {
        List<Category> categoryList = categoryLayer.getCategoryList();
        return new Response<>(categoryList, CategoryResponseMessage.SUCCESSFUL);
    }
}
