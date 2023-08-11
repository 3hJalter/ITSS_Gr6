package utils.response.responseMessageImpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.response.ResponseMessage;


@Getter
@AllArgsConstructor
public enum DockResponseMessage implements ResponseMessage {

    DOCK_NOT_EXIST("400_D0", "Dock does not exist"),
    DOCK_ID_IS_INVALID("400_D1", "Dock ID is invalid"),
    SUCCESSFUL("200_D0", "Successful");
    
    private final String code;
    private final String message;
}
