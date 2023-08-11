package utils.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private T object;
    private String code;
    private String message;

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
}
