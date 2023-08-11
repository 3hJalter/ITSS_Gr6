package validation;

import database.entityLayer.impl.DockLayer;
import entity.Dock;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.DockResponseMessage;

public class DockValidation {
    private static boolean isExist(Integer id){
        for (Dock dock : DockLayer.getInstance().getList()) {
            if (dock.getDockId().equals(id)) return true;
        }
        return false;
    }

    private static boolean isId(Integer id){
        return id != null;
    }

    public static ResponseMessage validate(Integer id, Dock dock){
        if (!isId(id)) return DockResponseMessage.DOCK_ID_IS_INVALID;
        if (!isExist(id)) return DockResponseMessage.DOCK_NOT_EXIST;
        return DockResponseMessage.SUCCESSFUL;
    }
}
