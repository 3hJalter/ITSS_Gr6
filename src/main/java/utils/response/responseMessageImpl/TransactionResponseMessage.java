package utils.response.responseMessageImpl;

import utils.response.ResponseMessage;

/**
 * The TransactionResponseMessage enum provides predefined response messages
 * for various transaction-related scenarios.
 */
public enum TransactionResponseMessage implements ResponseMessage {

    /**
     * Response message for the case where the transaction does not exist.
     */
    TRANSACTION_NOT_EXIST("400_T0", "Transaction does not exist"),

    /**
     * Response message for the case where the transaction ID is invalid.
     */
    TRANSACTION_ID_IS_INVALID("400_T1", "Transaction ID is invalid"),

    /**
     * Response message for the case where creating a new transaction fails.
     */
    CAN_NOT_CREATE_TRANSACTION("400_T2", "Fail to create new transaction"),

    /**
     * Response message for the case where a customer already has a rented bike.
     */
    CUSTOMER_ALREADY_RENT_BIKE("400_T3", "Customer already rent bike"),

    /**
     * Generic successful response message.
     */
    SUCCESSFUL("200_T0", "Successful");

    private final String code;
    private final String message;

    /**
     * Constructs a TransactionResponseMessage enum value with the provided code and message.
     *
     * @param code    The response code.
     * @param message The response message.
     */
    TransactionResponseMessage(String code, String message) {
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
