package utils.response.responseMessageImpl;

import utils.response.ResponseMessage;

/**
 * The CustomerResponseMessage enum provides predefined response messages
 * for various customer-related scenarios.
 */
public enum CustomerResponseMessage implements ResponseMessage {

    /**
     * Response message for the case where the customer does not exist.
     */
    CUSTOMER_NOT_EXIST("400_CU0", "Customer does not exist"),

    /**
     * Response message for the case where the customer ID is invalid.
     */
    CUSTOMER_ID_IS_INVALID("400_CU1", "Customer ID is invalid"),

    /**
     * Generic successful response message.
     */
    SUCCESSFUL("200_CU0", "Successful");

    private final String code;
    private final String message;

    /**
     * Constructs a CustomerResponseMessage enum value with the provided code and message.
     *
     * @param code    The response code.
     * @param message The response message.
     */
    CustomerResponseMessage(String code, String message) {
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
