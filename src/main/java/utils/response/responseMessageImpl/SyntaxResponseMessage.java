package utils.response.responseMessageImpl;

import utils.response.ResponseMessage;

public enum SyntaxResponseMessage implements ResponseMessage {

    SUCCESSFUL("200_S0", "Successful"),
    INVALID_ID_INPUT("400_S1", "Invalid input ID"),
    INVALID_BARCODE_UUID("400_S2", "Invalid barcode UUID"),
    INVALID_MONTH_INPUT("400_S3", "Invalid input month"),
    INVALID_YEAR_INPUT("400_S3", "Invalid input year"),
    INVALID_CARD_NUMBER("400_S4", "Invalid card number" ),
    INVALID_CARDHOLDER_NAME("400_S5", "Invalid cardholder name"),
    INVALID_ISSUING_BANK("400_S6", "Invalid issuing bank"),
    INVALID_SECURITY_CODE("400_S7", "Invalid security code"),
    INVALID_AMOUNT_INPUT("400_S8", "Invalid input amount"),;

    private final String code;
    private final String message;

    SyntaxResponseMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
