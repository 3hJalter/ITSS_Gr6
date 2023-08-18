package subsystem.interbank.controller;

import subsystem.interbank.database.CreditCardLayer;
import subsystem.interbank.entity.CreditCard;
import subsystem.interbank.validation.CreditCardResponseMessage;
import subsystem.interbank.validation.CreditCardValidation;
import utils.response.Response;
import utils.response.ResponseMessage;

public class CreditCardController {
    private static CreditCardController instance;
    private static CreditCardLayer creditCardLayer;

    CreditCardController() {
        creditCardLayer = CreditCardLayer.getInstance();
    }

    public static CreditCardController getInstance() {
        if (instance == null) {
            instance = new CreditCardController();
        }
        return instance;
    }

    public Response<Integer> payViaCard(String cardNumber, String cardholderName
            , String issueBank, int month, int year, String securityCode, Double amount) {
        ResponseMessage validation = CreditCardValidation.validate(cardNumber);
        if (validation != CreditCardResponseMessage.SUCCESSFUL)
            return new Response<>(null, validation);
        CreditCard creditCard = creditCardLayer.getCreditCardByCardNumber(cardNumber);
        validation = CreditCardValidation.authentication(creditCard, cardholderName
                , issueBank, month, year, securityCode);
        if (validation != CreditCardResponseMessage.SUCCESSFUL)
            return new Response<>(null, validation);
        Integer cardId = creditCardLayer.changeCardBalance(creditCard, amount, true);
        if (cardId == -1)
            return new Response<>(cardId, CreditCardResponseMessage.NOT_ENOUGH_BALANCE);
        return new Response<>(cardId, CreditCardResponseMessage.SUCCESSFUL);
    }

    public Response<Integer> receiveMoneyViaCard(String cardNumber, Double amount) {
        CreditCard creditCard = creditCardLayer.getCreditCardByCardNumber(cardNumber);
        if (creditCard == null) return new Response<>(null, CreditCardResponseMessage.CREDIT_CARD_NOT_EXIST);
        Integer status = creditCardLayer.changeCardBalance(creditCard, amount, false);
        if (status == -1)
            return new Response<>(status, CreditCardResponseMessage.INTERNAL_SERVER_ERROR);
        return new Response<>(status, CreditCardResponseMessage.SUCCESSFUL);
    }

    public Response<Double> getBalanceFromCard(String cardNumber) {
        ResponseMessage validation = CreditCardValidation.validate(cardNumber);
        if (validation != CreditCardResponseMessage.SUCCESSFUL)
            return new Response<>(null, validation);
        Double balance= creditCardLayer.getBalance(cardNumber);
        return new Response<>(balance, validation);
    }

    public Response<?> resetCard(String cardNumber) {
        ResponseMessage validation = CreditCardValidation.validate(cardNumber);
        if (validation != CreditCardResponseMessage.SUCCESSFUL)
            return new Response<>(null, validation);
        int status = creditCardLayer.resetBalance(cardNumber);
        if (status == -1) return new Response<>(null, CreditCardResponseMessage.INTERNAL_SERVER_ERROR);
        return new Response<>(null, CreditCardResponseMessage.SUCCESSFUL);
    }
}
