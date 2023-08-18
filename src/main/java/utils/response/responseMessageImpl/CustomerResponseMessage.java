package utils.response.responseMessageImpl;

import utils.response.ResponseMessage;
public enum CustomerResponseMessage implements ResponseMessage {

    CUSTOMER_NOT_EXIST("400_CU0", "Customer does not exist"),
    CUSTOMER_ID_IS_INVALID("400_CU1", "Customer ID is invalid"),
    SUCCESSFUL("200_CU0", "Successful");
    
    private final String code;
    private final String message;

    CustomerResponseMessage(String code, String message) {
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
