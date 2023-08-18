package category;

import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.CategoryResponseMessage;

/**
 * The CategoryValidation class provides methods for validating category-related operations.
 * It includes methods to check the existence and validity of category IDs.
 */
public class CategoryValidation {

    /**
     * Checks if a category with the given ID exists in the category list.
     *
     * @param id The ID of the category to check for existence.
     * @return {@code true} if a category with the specified ID exists, {@code false} otherwise.
     */
    private static boolean isExist(Integer id) {
        for (Category category : CategoryLayer.getInstance().getCategoryList()) {
            if (category.getCategoryId().equals(id)) return true;
        }
        return false;
    }

    /**
     * Checks if the provided ID is valid (not null).
     *
     * @param id The ID to be checked for validity.
     * @return {@code true} if the ID is valid (not null), {@code false} otherwise.
     */
    private static boolean isId(Integer id) {
        return id != null;
    }

    /**
     * Validates a category ID by checking its existence and validity.
     *
     * @param id The ID of the category to be validated.
     * @return A ResponseMessage indicating the validation result. Possible values:
     *         - {@link CategoryResponseMessage#SUCCESSFUL} if the ID is valid and exists.
     *         - {@link CategoryResponseMessage#CATEGORY_ID_IS_INVALID} if the ID is invalid.
     *         - {@link CategoryResponseMessage#CATEGORY_NOT_EXIST} if the category does not exist.
     */
    public static ResponseMessage validate(Integer id) {
        if (!isId(id)) return CategoryResponseMessage.CATEGORY_ID_IS_INVALID;
        if (!isExist(id)) return CategoryResponseMessage.CATEGORY_NOT_EXIST;
        return CategoryResponseMessage.SUCCESSFUL;
    }
}
