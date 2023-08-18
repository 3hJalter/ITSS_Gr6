package utils.response.responseMessageImpl;

import utils.response.ResponseMessage;

/**
 * The DockResponseMessage enum provides predefined response messages
 * for various dock-related scenarios.
 */
public enum DockResponseMessage implements ResponseMessage {

    /**
     * Response message for the case where the dock does not exist.
     */
    DOCK_NOT_EXIST("400_D0", "Dock does not exist"),

    /**
     * Response message for the case where the dock ID is invalid.
     */
    DOCK_ID_IS_INVALID("400_D1", "Dock ID is invalid"),

    /**
     * Generic successful response message.
     */
    SUCCESSFUL("200_D0", "Successful");

    private final String code;
    private final String message;

    /**
     * Constructs a DockResponseMessage enum value with the provided code and message.
     *
     * @param code    The response code.
     * @param message The response message.
     */
    DockResponseMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Retrieves the response code associated with the enum value.
     *
     * @return A String representing the response code.
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Retrieves the response message associated with the enum value.
     *
     * @return A String containing the response message.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
