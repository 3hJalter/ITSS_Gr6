package utils.response.responseMessageImpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.response.ResponseMessage;


@Getter
@AllArgsConstructor
public enum CategoryResponseMessage implements ResponseMessage {

    CATEGORY_NOT_EXIST("400_D0", "Category does not exist"),
    CATEGORY_ID_IS_INVALID("400_D1", "Category ID is invalid"),
    SUCCESSFUL("200_D0", "Successful");
    
    private final String code;
    private final String message;
}
