package utils.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private T object;
    private String errorCode;
    private String errorMessage;

    public Response(T object, ResponseMessage responseMessage) {
        this.object = object;
        this.errorCode = responseMessage.getCode();
        this.errorMessage = responseMessage.getMessage();
    }

    public Response(Exception e) {
        this.errorCode = "400";
        this.errorMessage = e.getCause().getMessage();
    }

    public Response(ResponseMessage responseMessage) {
        this.errorCode = responseMessage.getCode();
        this.errorMessage = responseMessage.getMessage();
    }
}
