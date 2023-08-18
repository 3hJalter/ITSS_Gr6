package utils.response.responseMessageImpl;

import utils.response.ResponseMessage;

/**
 * The InvoiceResponseMessage enum provides predefined response messages
 * for various invoice-related scenarios.
 */
public enum InvoiceResponseMessage implements ResponseMessage {

    /**
     * Response message for the case where the invoice does not exist.
     */
    INVOICE_NOT_EXIST("400_I0", "Invoice does not exist"),

    /**
     * Response message for the case where the invoice ID is invalid.
     */
    INVOICE_ID_IS_INVALID("400_I1", "Invoice ID is invalid"),

    /**
     * Response message for the case where creating a new invoice fails.
     */
    CAN_NOT_CREATE_INVOICE("400_T2", "Fail to create new Invoice"),

    /**
     * Response message for the case where the transaction is inactive.
     */
    TRANSACTION_IS_INACTIVE("400_T3", "Current transaction is inactive"),

    /**
     * Generic successful response message.
     */
    SUCCESSFUL("200_I0", "Successful");

    private final String code;
    private final String message;

    /**
     * Constructs an InvoiceResponseMessage enum value with the provided code and message.
     *
     * @param code    The response code.
     * @param message The response message.
     */
    InvoiceResponseMessage(String code, String message) {
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
