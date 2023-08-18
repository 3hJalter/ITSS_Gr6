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

public class TransactionController {
    private static TransactionController instance;
    private static TransactionLayer transactionLayer;

    TransactionController() {
        transactionLayer = TransactionLayer.getInstance();
    }

    public static TransactionController getInstance() {
        if (instance == null) {
            instance = new TransactionController();
        }
        return instance;
    }

    public Response<List<Transaction>> getTransactionList() {
        List<Transaction> transactionList = transactionLayer.getTransactionList();
        return new Response<>(transactionList, TransactionResponseMessage.SUCCESSFUL);
    }

    public Response<ActiveTransaction> getActiveTransactionByCustomerId(Integer customerId) {
        ResponseMessage validateMessage = CustomerValidation.validate(customerId);
        if (validateMessage != CustomerResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Transaction transaction = transactionLayer.getActiveTransactionByCustomerId(customerId);
        if (transaction == null) return new Response<>(null, TransactionResponseMessage.TRANSACTION_NOT_EXIST);
        Long currentPrice = transaction.getTransactionType().equals("24h")
                ? PriceMethod.get24hTotalPrice(transaction)
                : PriceMethod.getTotalPrice(transaction);
        ActiveTransaction ati = new ActiveTransaction(transaction,
                currentPrice, PriceMethod.getTimeRentInMinutes(transaction));
        return new Response<>(ati, TransactionResponseMessage.SUCCESSFUL);
    }

    public Response<?> createTransaction(Integer customerId, UUID barcode, String transactionType
            , String cardNumber, String cardholderName
            , String issueBank, int month, int year, String securityCode) {
        // Transaction without credit card, need modify when have interbank subsystem
        ResponseMessage validateMessage = CustomerValidation.validate(customerId);
        if (validateMessage != CustomerResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        validateMessage = TransactionValidation.validateCreation(customerId);
        if (validateMessage != TransactionResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Bike bike = BikeLayer.getInstance().getBikeByBarcode(barcode);
        if (bike == null) return new Response<>(null, BikeResponseMessage.BIKE_NOT_EXIST);
        validateMessage = BikeValidation.validate(bike.getBikeId(), bike);
        if (validateMessage != BikeResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);

        // Call Interbank to pay deposit here
        long deposit = PriceMethod.getDeposit(bike.getBikeId());
        String message = APIInterbankHandlers.payWithCard(cardNumber, cardholderName, issueBank, month, year,
                securityCode, deposit);
        if (!message.equals("Successful")) {
            return new Response<>(null, "501", message);
        }
        // End here
        int newTransactionId = transactionLayer.createTransaction(customerId, bike.getBikeId(), transactionType);
        if (newTransactionId == -1) {
            // Call Interbank to receive deposit here
            APIInterbankHandlers.receiveMoney(cardNumber, deposit);
            return new Response<>(null, TransactionResponseMessage.CAN_NOT_CREATE_TRANSACTION);
        }
        BikeLayer.getInstance().rentBikeById(bike.getBikeId());
        return new Response<>(null, TransactionResponseMessage.SUCCESSFUL);
    }

    public Response<Long> getDeposit(Integer bikeId) {
        Long deposit = PriceMethod.getDeposit(bikeId);
        return new Response<>(deposit, TransactionResponseMessage.SUCCESSFUL);
    }

    public Response<?> pauseTransaction(Integer transactionId) {
        ResponseMessage validateMessage = TransactionValidation.validate(transactionId);
        if (validateMessage != TransactionResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Transaction transaction = TransactionLayer.getInstance().getTransactionById(transactionId);
        TransactionLayer.getInstance().pauseTransaction(transaction);
        return new Response<>(null, TransactionResponseMessage.SUCCESSFUL);
    }

    public Response<?> unPauseTransaction(Integer transactionId) {
        ResponseMessage validateMessage = TransactionValidation.validate(transactionId);
        if (validateMessage != TransactionResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Transaction transaction = TransactionLayer.getInstance().getTransactionById(transactionId);
        TransactionLayer.getInstance().unPauseTransaction(transaction);
        return new Response<>(null, TransactionResponseMessage.SUCCESSFUL);
    }

    private record ActiveTransaction(Transaction transaction, Long currentPay, Long timeRent) {
    }
}
