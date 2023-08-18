package subsystem.interbank.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.response.ResponseMessage;


@Getter
@AllArgsConstructor
public enum CreditCardResponseMessage implements ResponseMessage {

    CREDIT_CARD_NOT_EXIST("400_CR0", "CreditCard does not exist"),
    CREDIT_CARD_ID_NUMBER_INVALID("400_CR1", "CreditCard Number is invalid"),
    CARDHOLDER_NAME_INVALID("400_CR2", "Cardholder name is invalid"),
    ISSUING_BANK_IS_INVALID("400_CR3", "Issuing Bank is invalid"),
    EXPIRATION_DATE_IS_INVALID("400_CR4", "Expiration Date is invalid"),
    WRONG_EXPIRATION_DATE("400_CR5", "Wrong Expiration Date"),
    SECURITY_CODE_IS_INVALID("400_CR6", "Security Code is invalid"),
    NOT_ENOUGH_BALANCE("400_CR7", "Not enough Balance"),
    INTERNAL_SERVER_ERROR("500", "Internal server error"),
    SUCCESSFUL("200_CA0", "Successful");
    
    private final String code;
    private final String message;
}
