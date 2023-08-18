package utils.response;

/**
 * The ResponseMessage interface defines the structure of a response message
 * that includes a code and a corresponding message text.
 */
public interface ResponseMessage {

    /**
     * Retrieves the code associated with the response message.
     *
     * @return A String representing the response code.
     */
    String getCode();

    /**
     * Retrieves the message text associated with the response.
     *
     * @return A String containing the response message.
     */
    String getMessage();
}