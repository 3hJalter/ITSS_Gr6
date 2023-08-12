package utils.response.responseMessageImpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.response.ResponseMessage;


@Getter
@AllArgsConstructor
public enum InvoiceResponseMessage implements ResponseMessage {

    INVOICE_NOT_EXIST("400_I0", "Invoice does not exist"),
    INVOICE_ID_IS_INVALID("400_I1", "Invoice ID is invalid"),
    CAN_NOT_CREATE_INVOICE("400_T2", "Fail to create new Invoice"),
    TRANSACTION_IS_INACTIVE("400_T3", "Current transaction is inactive"),
    SUCCESSFUL("200_I0", "Successful")
    ;
    
    private final String code;
    private final String message;
}
