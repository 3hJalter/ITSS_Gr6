package utils.response.responseMessageImpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.response.ResponseMessage;

@Getter
@AllArgsConstructor
public enum BikeResponseMessage implements ResponseMessage {

    BIKE_RESPONSE_MESSAGE("400_D0", "Bike does not exist"),
    BIKE_ID_IS_NULL("400_D1", "Bike ID is null"),
    BIKE_ID_NOT_FOUND("400_D2", "Bike ID not found"),
    BIKE_PIN_OUT_OF_RANGE("400_D3", "Bike PIN should be between 0 and 100"),
    BIKE_STATUS_IS_NULL("400_D4", "Bike status must not be null"),
    SUCCESSFUL("200_D0", "Successful");

    private final String code;
    private final String message;
}
