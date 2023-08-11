package validation;

import database.entityLayer.impl.CategoryLayer;
import entity.Category;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.CategoryResponseMessage;

public class CategoryValidation {
    private static boolean isExist(Integer id){
        for (Category category : CategoryLayer.getInstance().getList()) {
            if (category.getCategoryId().equals(id)) return true;
        }
        return false;
    }

    private static boolean isId(Integer id){
        return id != null;
    }

    public static ResponseMessage validate(Integer id, Category category){
        if (isId(id)) return CategoryResponseMessage.CATEGORY_ID_IS_INVALID;
        if (!isExist(id)) return CategoryResponseMessage.CATEGORY_NOT_EXIST;
        return CategoryResponseMessage.SUCCESSFUL;
    }
}
