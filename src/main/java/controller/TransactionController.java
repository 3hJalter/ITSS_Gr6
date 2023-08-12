package controller;

import database.entityLayer.BikeLayer;
import database.entityLayer.TransactionLayer;
import entity.Transaction;
import entity.bike.Bike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.PriceMethod;
import utils.response.Response;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.BikeResponseMessage;
import utils.response.responseMessageImpl.CustomerResponseMessage;
import utils.response.responseMessageImpl.TransactionResponseMessage;
import validation.BikeValidation;
import validation.CustomerValidation;
import validation.TransactionValidation;

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

    public Response<?> createTransaction(Integer customerId, UUID barcode, String transactionType){
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
        int newTransactionId = transactionLayer.createTransaction(customerId, bike.getBikeId(), transactionType);
        if (newTransactionId == -1) return new Response<>(null, TransactionResponseMessage.CAN_NOT_CREATE_TRANSACTION);
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

    @AllArgsConstructor
    @Getter
    public static class ActiveTransaction {
        private Transaction transaction;
        private Long currentPay;
        private Long timeRent;

        public String convertToString() {
            return "ActiveTransactionInfo{" +
                    "transaction=" + transaction +
                    ", currentPay=" + currentPay +
                    ", timeRent=" + timeRent +
                    '}';
        }
    }
}
