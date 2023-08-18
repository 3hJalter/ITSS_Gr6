package utils.response;

public class Response<T> {
    private final String code;
    private final String message;
    private T object;

    public Response(T object, ResponseMessage responseMessage) {
        this.object = object;
        this.code = responseMessage.getCode();
        this.message = responseMessage.getMessage();
    }

    public Response(Exception e) {
        this.code = "400";
        this.message = e.getCause().getMessage();
    }

    public Response(ResponseMessage responseMessage) {
        this.code = responseMessage.getCode();
        this.message = responseMessage.getMessage();
    }

    public Response(T object, String code, String message) {
        this.object = object;
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getObject() {
        return object;
    }
}
