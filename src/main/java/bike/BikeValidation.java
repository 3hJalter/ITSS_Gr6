package bike;

import bike.bikeEntity.Bike;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.BikeResponseMessage;

public class BikeValidation {
    private static boolean isExist(Integer id){
        for (Bike bike : BikeLayer.getInstance().getBikeList()) {
            if (bike.getBikeId().equals(id)) return true;
        }
        return false;
    }

    private static boolean isId(Integer id){
        return id != null;
    }

    private static boolean isBikeAlreadyRent (Bike bike) {
        return bike.getDock() == null;
    }

    public static ResponseMessage validate(Integer id, Bike bike){
        if (!isId(id)) return BikeResponseMessage.BIKE_ID_IS_INVALID;
        if (!isExist(id)) return BikeResponseMessage.BIKE_NOT_EXIST;
        if (bike != null && isBikeAlreadyRent(bike)) return BikeResponseMessage.BIKE_ALREADY_RENTED;
        return BikeResponseMessage.SUCCESSFUL;
    }
    
}
