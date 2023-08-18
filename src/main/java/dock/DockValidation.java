package dock;

import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.DockResponseMessage;

/**
 * The DockValidation class provides methods for validating dock-related operations.
 * It includes methods to check the existence and validity of dock IDs.
 */
public class DockValidation {

    /**
     * Checks if a dock with the given ID exists in the dock list.
     *
     * @param id The ID of the dock to check for existence.
     * @return {@code true} if a dock with the specified ID exists, {@code false} otherwise.
     */
    private static boolean isExist(Integer id) {
        for (Dock dock : DockLayer.getInstance().getDockList()) {
            if (dock.getDockId().equals(id)) return true;
        }
        return false;
    }

    /**
     * Checks if the provided dock ID is valid (not null).
     *
     * @param id The ID to be checked for validity.
     * @return {@code true} if the dock ID is valid (not null), {@code false} otherwise.
     */
    private static boolean isId(Integer id) {
        return id != null;
    }

    /**
     * Validates a dock ID by checking its existence and validity.
     *
     * @param id The ID of the dock to be validated.
     * @return A ResponseMessage indicating the validation result. Possible values:
     *         - {@link DockResponseMessage#SUCCESSFUL} if the ID is valid and the dock exists.
     *         - {@link DockResponseMessage#DOCK_ID_IS_INVALID} if the ID is invalid.
     *         - {@link DockResponseMessage#DOCK_NOT_EXIST} if the dock does not exist.
     */
    public static ResponseMessage validate(Integer id) {
        if (!isId(id)) return DockResponseMessage.DOCK_ID_IS_INVALID;
        if (!isExist(id)) return DockResponseMessage.DOCK_NOT_EXIST;
        return DockResponseMessage.SUCCESSFUL;
    }
}
