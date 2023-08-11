package controller;

import database.entityLayer.impl.DockLayer;
import entity.Dock;
import utils.response.Response;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.DockResponseMessage;
import validation.DockValidation;

import javax.print.Doc;
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
        List<Dock> dockList = dockLayer.getList();
        return new Response<>(dockList, DockResponseMessage.SUCCESSFUL);
    }

    public Response<List<Dock>> searchDock(String keyword) {
        List<Dock> dockList = dockLayer.search(keyword);
        return new Response<>(dockList, DockResponseMessage.SUCCESSFUL);
    }

    public Response<Dock> getDockById(Integer id) {
        ResponseMessage validateMessage = DockValidation.validate(id, null);
        if (validateMessage != DockResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Dock dock = dockLayer.getById(id);
        return new Response<>(dock, DockResponseMessage.SUCCESSFUL);
    }

}
