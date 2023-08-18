package subsystem.interbank.creditCard;

import utils.response.Response;
import utils.response.ResponseMessage;

/**
 * The CreditCardController class provides methods to manage credit card transactions and balances.
 * It interacts with the CreditCardLayer to perform credit card-related operations.
 */
public class CreditCardController {
    private static CreditCardController instance;
    private static CreditCardLayer creditCardLayer;

    /**
     * Private constructor initializes the CreditCardLayer instance.
     */
    CreditCardController() {
        creditCardLayer = CreditCardLayer.getInstance();
    }

    /**
     * Returns the instance of CreditCardController. If an instance doesn't exist, it creates one.
     *
     * @return The CreditCardController instance.
     */
    public static CreditCardController getInstance() {
        if (instance == null) {
            instance = new CreditCardController();
        }
        return instance;
    }

    /**
     * Performs a payment transaction using a credit card.
     *
     * @param cardNumber     The credit card number.
     * @param cardholderName The name of the cardholder.
     * @param issueBank      The issuing bank of the credit card.
     * @param month          The expiry month of the credit card.
     * @param year           The expiry year of the credit card.
     * @param securityCode   The security code of the credit card.
     * @param amount         The amount to be paid.
     * @return A Response object containing the transaction status and a response message.
     */
    public Response<Integer> payViaCard(String cardNumber, String cardholderName, String issueBank, int month, int year, String securityCode, Double amount) {
        ResponseMessage validation = CreditCardValidation.validate(cardNumber);
        if (validation != CreditCardResponseMessage.SUCCESSFUL) return new Response<>(null, validation);
        CreditCard creditCard = creditCardLayer.getCreditCardByCardNumber(cardNumber);
        validation = CreditCardValidation.authentication(creditCard, cardholderName, issueBank, month, year, securityCode);
        if (validation != CreditCardResponseMessage.SUCCESSFUL) return new Response<>(null, validation);
        Integer cardId = creditCardLayer.changeCardBalance(creditCard, amount, true);
        if (cardId == -1) return new Response<>(cardId, CreditCardResponseMessage.NOT_ENOUGH_BALANCE);
        return new Response<>(cardId, CreditCardResponseMessage.SUCCESSFUL);
    }

    /**
     * Receives money on a credit card.
     *
     * @param cardNumber The credit card number.
     * @param amount     The amount to be received.
     * @return A Response object containing the transaction status and a response message.
     */
    public Response<Integer> receiveMoneyViaCard(String cardNumber, Double amount) {
        CreditCard creditCard = creditCardLayer.getCreditCardByCardNumber(cardNumber);
        if (creditCard == null) return new Response<>(null, CreditCardResponseMessage.CREDIT_CARD_NOT_EXIST);
        Integer status = creditCardLayer.changeCardBalance(creditCard, amount, false);
        if (status == -1) return new Response<>(status, CreditCardResponseMessage.INTERNAL_SERVER_ERROR);
        return new Response<>(status, CreditCardResponseMessage.SUCCESSFUL);
    }

    /**
     * Retrieves the balance from a credit card.
     *
     * @param cardNumber The credit card number.
     * @return A Response object containing the balance and a response message.
     */
    public Response<Double> getBalanceFromCard(String cardNumber) {
        ResponseMessage validation = CreditCardValidation.validate(cardNumber);
        if (validation != CreditCardResponseMessage.SUCCESSFUL) return new Response<>(null, validation);
        Double balance = creditCardLayer.getBalance(cardNumber);
        return new Response<>(balance, validation);
    }

    /**
     * Resets the balance of a credit card to its original state.
     *
     * @param cardNumber The credit card number.
     * @return A Response object containing a response message.
     */
    public Response<?> resetCard(String cardNumber) {
        ResponseMessage validation = CreditCardValidation.validate(cardNumber);
        if (validation != CreditCardResponseMessage.SUCCESSFUL) return new Response<>(null, validation);
        int status = creditCardLayer.resetBalance(cardNumber);
        if (status == -1) return new Response<>(null, CreditCardResponseMessage.INTERNAL_SERVER_ERROR);
        return new Response<>(null, CreditCardResponseMessage.SUCCESSFUL);
    }
}
