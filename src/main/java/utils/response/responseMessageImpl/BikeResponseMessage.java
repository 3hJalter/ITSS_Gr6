package utils.response.responseMessageImpl;

import utils.response.ResponseMessage;

public enum BikeResponseMessage implements ResponseMessage {

    BIKE_NOT_EXIST("400_B0", "Bike does not exist"),
    BIKE_ID_IS_INVALID("400_B1", "Bike ID is invalid"),
    BIKE_ID_NOT_FOUND("400_B2", "Bike ID not found"),
    BIKE_PIN_OUT_OF_RANGE("400B3", "Bike PIN should be between 0 and 100"),
    BIKE_STATUS_IS_NULL("400_B4", "Bike status must not be null"),
    BIKE_ALREADY_RENTED("400_B5", "Bike already rented"),
    SUCCESSFUL("200_B0", "Successful");

    private final String code;
    private final String message;

    BikeResponseMessage(String code, String message) {
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
