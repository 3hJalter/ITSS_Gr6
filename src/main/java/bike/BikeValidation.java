package bike;

import bike.bikeEntity.Bike;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.BikeResponseMessage;

/**
 * This class provides methods for validating bike-related data and states.
 */
public class BikeValidation {

    /**
     * Checks if a bike with the given ID exists in the bike list.
     *
     * @param id The ID of the bike to check.
     * @return True if a bike with the given ID exists, otherwise false.
     */
    private static boolean isExist(Integer id){
        for (Bike bike : BikeLayer.getInstance().getBikeList()) {
            if (bike.getBikeId().equals(id)) return true;
        }
        return false;
    }

    /**
     * Checks if the given ID is valid (not null).
     *
     * @param id The ID to check for validity.
     * @return True if the ID is not null, otherwise false.
     */
    private static boolean isId(Integer id){
        return id != null;
    }

    /**
     * Checks if a bike is already rented (i.e., associated with a dock).
     *
     * @param bike The bike to check for rental status.
     * @return True if the bike is already rented, otherwise false.
     */
    private static boolean isBikeAlreadyRent(Bike bike) {
        return bike.getDock() == null;
    }

    /**
     * Validates the bike data and states based on the provided parameters.
     *
     * @param id The ID of the bike to validate.
     * @param bike The bike object to validate for rental status.
     * @return A ResponseMessage indicating the validation result.
     *         - BIKE_ID_IS_INVALID if the bike ID is invalid (null).
     *         - BIKE_NOT_EXIST if the bike with the given ID does not exist.
     *         - BIKE_ALREADY_RENTED if the provided bike is already rented.
     *         - SUCCESSFUL if the validation is successful and the bike is ready for use.
     */
    public static ResponseMessage validate(Integer id, Bike bike){
        if (!isId(id)) return BikeResponseMessage.BIKE_ID_IS_INVALID;
        if (!isExist(id)) return BikeResponseMessage.BIKE_NOT_EXIST;
        if (bike != null && isBikeAlreadyRent(bike)) return BikeResponseMessage.BIKE_ALREADY_RENTED;
        return BikeResponseMessage.SUCCESSFUL;
    }
}
