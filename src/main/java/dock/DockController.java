package dock;

import utils.response.Response;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.DockResponseMessage;

import java.util.List;

/**
 * The DockController class provides methods to interact with docks through the DockLayer.
 * It is responsible for managing dock-related operations and responses.
 */
public class DockController {

    private static DockController instance;
    private static DockLayer dockLayer;

    /**
     * Private constructor initializes the DockLayer instance.
     */
    private DockController() {
        dockLayer = DockLayer.getInstance();
    }

    /**
     * Returns the instance of DockController. If an instance doesn't exist, it creates one.
     *
     * @return The DockController instance.
     */
    public static DockController getInstance() {
        if (instance == null) {
            instance = new DockController();
        }
        return instance;
    }

    /**
     * Retrieves a list of all docks.
     *
     * @return A Response object containing the list of docks and a response message.
     */
    public Response<List<Dock>> getDockList() {
        List<Dock> dockList = dockLayer.getDockList();
        return new Response<>(dockList, DockResponseMessage.SUCCESSFUL);
    }

    /**
     * Searches for docks based on the provided keyword.
     *
     * @param keyword The keyword to search for in dock names.
     * @return A Response object containing the list of matching docks and a response message.
     */
    public Response<List<Dock>> searchDock(String keyword) {
        List<Dock> dockList = dockLayer.searchDock(keyword);
        return new Response<>(dockList, DockResponseMessage.SUCCESSFUL);
    }

    /**
     * Retrieves a dock by its unique identifier.
     *
     * @param id The identifier of the dock to retrieve.
     * @return A Response object containing the retrieved dock and a response message.
     */
    public Response<Dock> getDockById(Integer id) {
        ResponseMessage validateMessage = DockValidation.validate(id);
        if (validateMessage != DockResponseMessage.SUCCESSFUL) {
            return new Response<>(null, validateMessage);
        }
        Dock dock = dockLayer.getDockById(id);
        return new Response<>(dock, DockResponseMessage.SUCCESSFUL);
    }
}
