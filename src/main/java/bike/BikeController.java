package bike;

import bike.bikeEntity.Bike;
import bike.bikeEntity.EBike;
import category.CategoryValidation;
import dock.DockValidation;
import utils.response.Response;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.BikeResponseMessage;
import utils.response.responseMessageImpl.CategoryResponseMessage;
import utils.response.responseMessageImpl.DockResponseMessage;

import java.util.List;
import java.util.UUID;

/**
 * The BikeController class provides methods to interact with bike-related operations and data.
 * It acts as an interface between the application's business logic and the underlying data layer.
 */
public class BikeController {

    private static BikeController instance;
    private static BikeLayer bikeLayer;

    /**
     * Private constructor to initialize the BikeLayer instance.
     */
    private BikeController() {
        bikeLayer = BikeLayer.getInstance();
    }

    /**
     * Get the singleton instance of the BikeController class.
     *
     * @return The singleton instance of BikeController.
     */
    public static BikeController getInstance() {
        if (instance == null) {
            instance = new BikeController();
        }
        return instance;
    }

    /**
     * Get a list of all bikes.
     *
     * @return A Response containing a list of Bike objects on success, or an error message on failure.
     */
    public Response<List<Bike>> getBikeList() {
        List<Bike> bikeList = bikeLayer.getBikeList();
        return new Response<>(bikeList, BikeResponseMessage.SUCCESSFUL);
    }

    /**
     * Get a list of all e-bikes.
     *
     * @return A Response containing a list of EBike objects on success, or an error message on failure.
     */
    public Response<List<EBike>> getEBikeList() {
        List<EBike> bikeList = bikeLayer.getEBikeList();
        return new Response<>(bikeList, BikeResponseMessage.SUCCESSFUL);
    }

    /**
     * Get a list of bikes located at a specified dock.
     *
     * @param id The ID of the dock.
     * @return A Response containing a list of Bike objects at the specified dock on success, or an error message on failure.
     */
    public Response<List<Bike>> getBikeByDockId(Integer id) {
        ResponseMessage validateMessage = DockValidation.validate(id);
        if (validateMessage != DockResponseMessage.SUCCESSFUL) return new Response<>(null, validateMessage);
        List<Bike> bikeList = bikeLayer.getBikeByDockId(id);
        return new Response<>(bikeList, BikeResponseMessage.SUCCESSFUL);
    }

    /**
     * Get a list of bikes belonging to a specified category.
     *
     * @param id The ID of the category.
     * @return A Response containing a list of Bike objects in the specified category on success, or an error message on failure.
     */
    public Response<List<Bike>> getBikeByCategoryId(Integer id) {
        ResponseMessage validateMessage = CategoryValidation.validate(id);
        if (validateMessage != CategoryResponseMessage.SUCCESSFUL) return new Response<>(null, validateMessage);
        List<Bike> bikeList = bikeLayer.getBikeByCategoryId(id);
        return new Response<>(bikeList, BikeResponseMessage.SUCCESSFUL);
    }

    /**
     * Get a Bike object by its ID.
     *
     * @param id The ID of the bike.
     * @return A Response containing the Bike object with the specified ID on success, or an error message on failure.
     */
    public Response<Bike> getBikeById(Integer id) {
        ResponseMessage validateMessage = BikeValidation.validate(id, null);
        if (validateMessage != BikeResponseMessage.SUCCESSFUL) return new Response<>(null, validateMessage);
        Bike bike = bikeLayer.getBikeById(id);
        return new Response<>(bike, BikeResponseMessage.SUCCESSFUL);
    }

    /**
     * Get a Bike object by its barcode.
     *
     * @param barcode The barcode of the bike.
     * @return A Response containing the Bike object with the specified barcode on success, or an error message on failure.
     */
    public Response<Bike> getBikeByBarcode(UUID barcode) {
        Bike bike = BikeLayer.getInstance().getBikeByBarcode(barcode);
        if (bike == null) return new Response<>(null, BikeResponseMessage.BIKE_NOT_EXIST);
        ResponseMessage validateMessage = BikeValidation.validate(bike.getBikeId(), bike);
        if (validateMessage != BikeResponseMessage.SUCCESSFUL) return new Response<>(null, validateMessage);
        return new Response<>(bike, BikeResponseMessage.SUCCESSFUL);
    }
}
