package utils.response.responseMessageImpl;

import utils.response.ResponseMessage;

/**
 * The SyntaxResponseMessage enum provides predefined response messages
 * for various syntax-related scenarios.
 */
public enum SyntaxResponseMessage implements ResponseMessage {

    /**
     * Generic successful response message.
     */
    SUCCESSFUL("200_S0", "Successful"),

    /**
     * Response message for the case of invalid input ID syntax.
     */
    INVALID_ID_INPUT("400_S1", "Invalid input ID"),

    /**
     * Response message for the case of invalid barcode UUID syntax.
     */
    INVALID_BARCODE_UUID("400_S2", "Invalid barcode UUID"),

    /**
     * Response message for the case of invalid input month syntax.
     */
    INVALID_MONTH_INPUT("400_S3", "Invalid input month"),

    /**
     * Response message for the case of invalid input year syntax.
     */
    INVALID_YEAR_INPUT("400_S3", "Invalid input year"),

    /**
     * Response message for the case of invalid card number syntax.
     */
    INVALID_CARD_NUMBER("400_S4", "Invalid card number"),

    /**
     * Response message for the case of invalid cardholder name syntax.
     */
    INVALID_NAME("400_S5", "Invalid name"),

    /**
     * Response message for the case of invalid cardholder name syntax.
     */
    INVALID_CARDHOLDER_NAME("400_S6", "Invalid cardholder name"),

    /**
     * Response message for the case of invalid issuing bank syntax.
     */
    INVALID_ISSUING_BANK("400_S7", "Invalid issuing bank"),

    /**
     * Response message for the case of invalid security code syntax.
     */
    INVALID_SECURITY_CODE("400_S8", "Invalid security code"),

    /**
     * Response message for the case of invalid input amount syntax.
     */
    INVALID_AMOUNT_INPUT("400_S9", "Invalid input amount");

    private final String code;
    private final String message;

    /**
     * Constructs a SyntaxResponseMessage enum value with the provided code and message.
     *
     * @param code    The response code.
     * @param message The response message.
     */
    SyntaxResponseMessage(String code, String message) {
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
