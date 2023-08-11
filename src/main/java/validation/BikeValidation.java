package validation;

import database.entityLayer.BikeLayer;
import entity.Bike;
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

    public static ResponseMessage validate(Integer id){
        if (isId(id)) return BikeResponseMessage.BIKE_ID_IS_INVALID;
        if (!isExist(id)) return BikeResponseMessage.BIKE_NOT_EXIST;
        return BikeResponseMessage.SUCCESSFUL;
    }
    
}
