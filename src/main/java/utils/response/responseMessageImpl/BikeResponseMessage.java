package utils.response.responseMessageImpl;

import utils.response.ResponseMessage;

/**
 * The BikeResponseMessage enum provides predefined response messages
 * for various bike-related scenarios.
 */
public enum BikeResponseMessage implements ResponseMessage {

    /**
     * Response message for the case where the bike does not exist.
     */
    BIKE_NOT_EXIST("400_B0", "Bike does not exist"),

    /**
     * Response message for the case where the bike ID is invalid.
     */
    BIKE_ID_IS_INVALID("400_B1", "Bike ID is invalid"),

    /**
     * Response message for the case where the bike ID is not found.
     */
    BIKE_ID_NOT_FOUND("400_B2", "Bike ID not found"),

    /**
     * Response message for the case where the bike PIN is out of the valid range.
     */
    BIKE_PIN_OUT_OF_RANGE("400_B3", "Bike PIN should be between 0 and 100"),

    /**
     * Response message for the case where the bike status is null.
     */
    BIKE_STATUS_IS_NULL("400_B4", "Bike status must not be null"),

    /**
     * Response message for the case where the bike is already rented.
     */
    BIKE_ALREADY_RENTED("400_B5", "Bike already rented"),

    /**
     * Generic successful response message.
     */
    SUCCESSFUL("200_B0", "Successful");

    private final String code;
    private final String message;

    /**
     * Constructs a BikeResponseMessage enum value with the provided code and message.
     *
     * @param code    The response code.
     * @param message The response message.
     */
    BikeResponseMessage(String code, String message) {
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
