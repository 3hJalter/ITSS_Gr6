package subsystem.interbank.creditCard;

import utils.response.ResponseMessage;

/**
 * The CreditCardResponseMessage enum provides predefined response messages
 * for various credit card-related scenarios in the Interbank subsystem.
 */
public enum CreditCardResponseMessage implements ResponseMessage {

    /**
     * Response message for the case where the credit card does not exist.
     */
    CREDIT_CARD_NOT_EXIST("400_CR0", "CreditCard does not exist"),

    /**
     * Response message for the case where the credit card number is invalid.
     */
    CREDIT_CARD_ID_NUMBER_INVALID("400_CR1", "CreditCard Number is invalid"),

    /**
     * Response message for the case where the cardholder name is invalid.
     */
    CARDHOLDER_NAME_INVALID("400_CR2", "Cardholder name is invalid"),

    /**
     * Response message for the case where the issuing bank is invalid.
     */
    ISSUING_BANK_IS_INVALID("400_CR3", "Issuing Bank is invalid"),

    /**
     * Response message for the case where the expiration date is invalid.
     */
    EXPIRATION_DATE_IS_INVALID("400_CR4", "Expiration Date is invalid"),

    /**
     * Response message for the case where the expiration date is incorrect.
     */
    WRONG_EXPIRATION_DATE("400_CR5", "Wrong Expiration Date"),

    /**
     * Response message for the case where the security code is invalid.
     */
    SECURITY_CODE_IS_INVALID("400_CR6", "Security Code is invalid"),

    /**
     * Response message for the case where there is not enough balance on the credit card.
     */
    NOT_ENOUGH_BALANCE("400_CR7", "Not enough Balance"),

    /**
     * Response message for internal server errors.
     */
    INTERNAL_SERVER_ERROR("500", "Internal server error"),

    /**
     * Generic successful response message.
     */
    SUCCESSFUL("200_CA0", "Successful");

    private final String code;
    private final String message;

    /**
     * Constructs a CreditCardResponseMessage enum value with the provided code and message.
     *
     * @param code    The response code.
     * @param message The response message.
     */
    CreditCardResponseMessage(String code, String message) {
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
