package controller;

import database.entityLayer.BikeLayer;
import database.entityLayer.DockLayer;
import entity.Dock;
import entity.bike.Bike;
import utils.response.Response;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.DockResponseMessage;
import validation.DockValidation;

import java.util.List;

public class DockController {
    private static DockController instance;
    private static DockLayer dockLayer;

    DockController() {
        dockLayer = DockLayer.getInstance();
    }

    public static DockController getInstance() {
        if (instance == null) {
            instance = new DockController();
        }
        return instance;
    }

    public Response<List<Dock>> getDockList() {
        List<Dock> dockList = dockLayer.getDockList();
        return new Response<>(dockList, DockResponseMessage.SUCCESSFUL);
    }

    public Response<List<Dock>> searchDock(String keyword) {
        List<Dock> dockList = dockLayer.searchDock(keyword);
        return new Response<>(dockList, DockResponseMessage.SUCCESSFUL);
    }

    public Response<List<Bike>> getBikeByDockId(Integer dockId) {
        ResponseMessage validateMessage = DockValidation.validate(dockId);
        if (validateMessage != DockResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        List<Bike> bikeList = BikeLayer.getInstance().getBikeByDockId(dockId);
        return new Response<>(bikeList, DockResponseMessage.SUCCESSFUL);
    }

    public Response<Dock> getDockById(Integer id) {
        ResponseMessage validateMessage = DockValidation.validate(id);
        if (validateMessage != DockResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Dock dock = dockLayer.getDockById(id);
        return new Response<>(dock, DockResponseMessage.SUCCESSFUL);
    }

}
