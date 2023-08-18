package utils.response.responseMessageImpl;

import utils.response.ResponseMessage;

public enum DockResponseMessage implements ResponseMessage {

    DOCK_NOT_EXIST("400_D0", "Dock does not exist"),
    DOCK_ID_IS_INVALID("400_D1", "Dock ID is invalid"),
    SUCCESSFUL("200_D0", "Successful");

    private final String code;
    private final String message;

    DockResponseMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
