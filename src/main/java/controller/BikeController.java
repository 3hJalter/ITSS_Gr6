package controller;

import database.entityLayer.BikeLayer;
import entity.bike.Bike;
import entity.bike.EBike;
import utils.response.Response;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.BikeResponseMessage;
import utils.response.responseMessageImpl.CategoryResponseMessage;
import utils.response.responseMessageImpl.DockResponseMessage;
import validation.BikeValidation;
import validation.CategoryValidation;
import validation.DockValidation;

import java.util.List;
import java.util.UUID;

public class BikeController {
    private static BikeController instance;
    private static BikeLayer bikeLayer;

    BikeController() {
        bikeLayer = BikeLayer.getInstance();
    }

    public static BikeController getInstance() {
        if (instance == null) {
            instance = new BikeController();
        }
        return instance;
    }

    public Response<List<Bike>> getBikeList() {
        List<Bike> bikeList = bikeLayer.getBikeList();
        return new Response<>(bikeList, BikeResponseMessage.SUCCESSFUL);
    }

    public Response<List<EBike>> getEBikeList() {
        List<EBike> bikeList = bikeLayer.getEBikeList();
        return new Response<>(bikeList, BikeResponseMessage.SUCCESSFUL);
    }

    public Response<List<Bike>> getBikeByDockId(Integer id) {
        ResponseMessage validateMessage = DockValidation.validate(id);
        if (validateMessage != DockResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        List<Bike> bikeList = bikeLayer.getBikeByDockId(id);
        return new Response<>(bikeList, BikeResponseMessage.SUCCESSFUL);
    }

    public Response<List<Bike>> getBikeByCategoryId(Integer id) {
        ResponseMessage validateMessage = CategoryValidation.validate(id);
        if (validateMessage != CategoryResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        List<Bike> bikeList = bikeLayer.getBikeByCategoryId(id);
        return new Response<>(bikeList, BikeResponseMessage.SUCCESSFUL);
    }

    public Response<Bike> getBikeById(Integer id) {
        ResponseMessage validateMessage = BikeValidation.validate(id, null);
        if (validateMessage != BikeResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Bike bike = bikeLayer.getBikeById(id);
        return new Response<>(bike, BikeResponseMessage.SUCCESSFUL);
    }

    public Response<Bike> getBikeByBarcode(UUID barcode) {
        Bike bike = BikeLayer.getInstance().getBikeByBarcode(barcode);
        if (bike == null) return new Response<>(null, BikeResponseMessage.BIKE_NOT_EXIST);
        ResponseMessage validateMessage = BikeValidation.validate(bike.getBikeId(), bike);
        if (validateMessage != BikeResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        return new Response<>(bike, BikeResponseMessage.SUCCESSFUL);
    }

}
