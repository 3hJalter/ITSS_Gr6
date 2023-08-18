package utils.response.responseMessageImpl;

import utils.response.ResponseMessage;

/**
 * The CategoryResponseMessage enum provides predefined response messages
 * for various category-related scenarios.
 */
public enum CategoryResponseMessage implements ResponseMessage {

    /**
     * Response message for the case where the category does not exist.
     */
    CATEGORY_NOT_EXIST("400_CA0", "Category does not exist"),

    /**
     * Response message for the case where the category ID is invalid.
     */
    CATEGORY_ID_IS_INVALID("400_CA1", "Category ID is invalid"),

    /**
     * Generic successful response message.
     */
    SUCCESSFUL("200_CA0", "Successful");

    private final String code;
    private final String message;

    /**
     * Constructs a CategoryResponseMessage enum value with the provided code and message.
     *
     * @param code    The response code.
     * @param message The response message.
     */
    CategoryResponseMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Retrieves the response code associated with the enum value.
     *
     * @return A String representing the response code.
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Retrieves the response message associated with the enum value.
     *
     * @return A String containing the response message.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
