package utils.response;

/**
 * The Response class represents a structured response object that includes a code,
 * a message, and an optional associated object of type T.
 *
 * @param <T> The type of object associated with the response.
 */
public class Response<T> {
    private final String code;
    private final String message;
    private T object;

    /**
     * Constructs a Response object with the provided associated object and response message.
     *
     * @param object         The associated object of type T.
     * @param responseMessage The response message containing code and message text.
     */
    public Response(T object, ResponseMessage responseMessage) {
        this.object = object;
        this.code = responseMessage.getCode();
        this.message = responseMessage.getMessage();
    }

    /**
     * Constructs a Response object with an error message extracted from the provided exception.
     *
     * @param e The exception from which to extract the error message.
     */
    public Response(Exception e) {
        this.code = "400";
        this.message = e.getCause().getMessage();
    }

    /**
     * Constructs a Response object with the provided response message.
     *
     * @param responseMessage The response message containing code and message text.
     */
    public Response(ResponseMessage responseMessage) {
        this.code = responseMessage.getCode();
        this.message = responseMessage.getMessage();
    }

    /**
     * Constructs a Response object with the provided associated object, code, and message.
     *
     * @param object The associated object of type T.
     * @param code   The response code.
     * @param message The response message text.
     */
    public Response(T object, String code, String message) {
        this.object = object;
        this.code = code;
        this.message = message;
    }

    /**
     * Retrieves the response code.
     *
     * @return A String representing the response code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retrieves the response message text.
     *
     * @return A String containing the response message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the associated object of type T.
     *
     * @return The associated object.
     */
    public T getObject() {
        return object;
    }
}
