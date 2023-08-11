package utils.response.responseMessageImpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.response.ResponseMessage;


@Getter
@AllArgsConstructor
public enum TransactionResponseMessage implements ResponseMessage {

    TRANSACTION_NOT_EXIST("400_T0", "Transaction does not exist"),
    TRANSACTION_ID_IS_INVALID("400_T1", "Transaction ID is invalid"),
    CAN_NOT_CREATE_TRANSACTION("400_T2", "Fail to create new transaction"),
    CUSTOMER_ALREADY_RENT_BIKE("400_T3", "Customer already rent bike"),
    SUCCESSFUL("200_T0", "Successful");
    
    private final String code;
    private final String message;
}
