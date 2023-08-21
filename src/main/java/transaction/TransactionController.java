package transaction;

import bike.BikeLayer;
import bike.BikeValidation;
import bike.bikeEntity.Bike;
import customer.CustomerValidation;
import utils.PriceMethod;
import utils.api.APIInterbankHandlers;
import utils.response.Response;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.BikeResponseMessage;
import utils.response.responseMessageImpl.CustomerResponseMessage;
import utils.response.responseMessageImpl.TransactionResponseMessage;

import java.util.List;
import java.util.UUID;

/**
 * The TransactionController class provides methods to manage and interact with transactions.
 * It interacts with the TransactionLayer to perform transaction-related operations.
 */
public class TransactionController {
    private static TransactionController instance;
    private static TransactionLayer transactionLayer;

    /**
     * Private constructor initializes the TransactionLayer instance.
     */
    TransactionController() {
        transactionLayer = TransactionLayer.getInstance();
    }

    /**
     * Returns the instance of TransactionController. If an instance doesn't exist, it creates one.
     *
     * @return The TransactionController instance.
     */
    public static TransactionController getInstance() {
        if (instance == null) {
            instance = new TransactionController();
        }
        return instance;
    }

    /**
     * Retrieves a list of all transactions.
     *
     * @return A Response object containing the list of transactions and a response message.
     */
    public Response<List<Transaction>> getTransactionList() {
        List<Transaction> transactionList = transactionLayer.getTransactionList();
        return new Response<>(transactionList, TransactionResponseMessage.SUCCESSFUL);
    }

    /**
     * Retrieves the active transaction for a given customer.
     *
     * @param customerId The identifier of the customer.
     * @return A Response object containing the active transaction information and a response message.
     */
    public Response<ActiveTransaction> getActiveTransactionByCustomerId(Integer customerId) {
        // Validate Customer
        ResponseMessage validateMessage = CustomerValidation.validate(customerId);
        if (validateMessage != CustomerResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        // Check null Transaction
        Transaction transaction = transactionLayer.getActiveTransactionByCustomerId(customerId);
        if (transaction == null) return new Response<>(null, TransactionResponseMessage.TRANSACTION_NOT_EXIST);
        // Get Active Transaction
        Long currentPrice = transaction.getTransactionType().equals("24h")
                ? PriceMethod.get24hTotalPrice(transaction)
                : PriceMethod.getTotalPrice(transaction);
        ActiveTransaction ati = new ActiveTransaction(transaction,
                currentPrice, PriceMethod.getTimeRentInMinutes(transaction));
        return new Response<>(ati, TransactionResponseMessage.SUCCESSFUL);
    }

    /**
     * Creates a new transaction with payment using a credit card.
     *
     * @param customerId     The identifier of the customer.
     * @param barcode        The barcode associated with the bike.
     * @param transactionType The type of the transaction (e.g., "24h").
     * @param cardNumber     The credit card number for payment.
     * @param cardholderName The name of the cardholder.
     * @param issueBank      The issuing bank of the credit card.
     * @param month          The expiry month of the credit card.
     * @param year           The expiry year of the credit card.
     * @param securityCode   The security code of the credit card.
     * @return A Response object with a success or error message.
     */
    public Response<?> createTransaction(Integer customerId, UUID barcode, String transactionType
            , String cardNumber, String cardholderName
            , String issueBank, String month, String year, String securityCode) {
        // Validate Customer
        ResponseMessage validateMessage = CustomerValidation.validate(customerId);
        if (validateMessage != CustomerResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        // Validate if Transaction can create
        validateMessage = TransactionValidation.validateCreation(customerId);
        if (validateMessage != TransactionResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        // Validate Bike
        Bike bike = BikeLayer.getInstance().getBikeByBarcode(barcode);
        if (bike == null) return new Response<>(null, BikeResponseMessage.BIKE_NOT_EXIST);
        validateMessage = BikeValidation.validate(bike.getBikeId(), bike);
        if (validateMessage != BikeResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        // Call Interbank to pay deposit
        long deposit = PriceMethod.getDeposit(bike.getBikeId());
        String message = APIInterbankHandlers.payWithCard(cardNumber, cardholderName, issueBank, month, year,
                securityCode, deposit);
        if (!message.equals("Successful")) {
            return new Response<>(null, "501", message);
        }
        // Create Transaction
        int newTransactionId = transactionLayer.createTransaction(customerId, bike.getBikeId(), transactionType);
        if (newTransactionId == -1) {
            // Call Interbank to receive deposit here if Transaction can not create
            APIInterbankHandlers.receiveMoney(cardNumber, deposit);
            return new Response<>(null, TransactionResponseMessage.CAN_NOT_CREATE_TRANSACTION);
        }
        // Rent Bike
        BikeLayer.getInstance().rentBikeById(bike.getBikeId());
        return new Response<>(null, TransactionResponseMessage.SUCCESSFUL);
    }

    /**
     * Retrieves the deposit amount for a given bike.
     *
     * @param bikeId The identifier of the bike.
     * @return A Response object containing the deposit amount and a response message.
     */
    public Response<Long> getDeposit(Integer bikeId) {
        Long deposit = PriceMethod.getDeposit(bikeId);
        return new Response<>(deposit, TransactionResponseMessage.SUCCESSFUL);
    }

    /**
     * Pauses a transaction.
     *
     * @param transactionId The identifier of the transaction to be paused.
     * @return A Response object with a success or error message.
     */
    public Response<?> pauseTransaction(Integer transactionId) {
        ResponseMessage validateMessage = TransactionValidation.validate(transactionId);
        if (validateMessage != TransactionResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Transaction transaction = TransactionLayer.getInstance().getTransactionById(transactionId);
        TransactionLayer.getInstance().pauseTransaction(transaction);
        return new Response<>(null, TransactionResponseMessage.SUCCESSFUL);
    }

    /**
     * Unpauses a transaction.
     *
     * @param transactionId The identifier of the transaction to be un-paused.
     * @return A Response object with a success or error message.
     */
    public Response<?> unPauseTransaction(Integer transactionId) {
        ResponseMessage validateMessage = TransactionValidation.validate(transactionId);
        if (validateMessage != TransactionResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Transaction transaction = TransactionLayer.getInstance().getTransactionById(transactionId);
        TransactionLayer.getInstance().unPauseTransaction(transaction);
        return new Response<>(null, TransactionResponseMessage.SUCCESSFUL);
    }

    /**
     * A private record class to represent an active transaction with additional details.
     */
    private record ActiveTransaction(Transaction transaction, Long currentPay, Long timeRent) {
    }
}
