package controller;

import database.entityLayer.BikeLayer;
import database.entityLayer.TransactionLayer;
import entity.Transaction;
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
        ActiveTransaction ati = new ActiveTransaction(transaction,
                 PriceMethod.getTotalPrice(transaction), PriceMethod.getTimeRentInMinutes(transaction));
        return new Response<>(ati, TransactionResponseMessage.SUCCESSFUL);
    }

    public Response<?> createTransaction(Integer customerId, Integer bikeId){
        // Transaction without credit card, need modify when have interbank subsystem
        ResponseMessage validateMessage = CustomerValidation.validate(customerId);
        if (validateMessage != CustomerResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        validateMessage = TransactionValidation.validateCreation(customerId);
        if (validateMessage != TransactionResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        validateMessage = BikeValidation.validate(bikeId);
        if (validateMessage != BikeResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        int newTransactionId = transactionLayer.createTransaction(customerId, bikeId);
        if (newTransactionId == -1) return new Response<>(null, TransactionResponseMessage.CAN_NOT_CREATE_TRANSACTION);
        BikeLayer.getInstance().rentBikeById(bikeId);
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
