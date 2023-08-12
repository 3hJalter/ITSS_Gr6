package controller;

import database.entityLayer.TransactionLayer;
import entity.Transaction;
import lombok.AllArgsConstructor;
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

    public Response<ActiveTransactionInfo> getActiveTransactionByCustomerId(Integer customerId) {
        ResponseMessage validateMessage = CustomerValidation.validate(customerId);
        if (validateMessage != CustomerResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Transaction transaction = transactionLayer.getActiveTransactionByCustomerId(customerId);
        if (transaction == null) return new Response<>(null, TransactionResponseMessage.TRANSACTION_NOT_EXIST);
        ActiveTransactionInfo ati = new ActiveTransactionInfo(transaction,
                 PriceMethod.getTotalPrice(transaction), PriceMethod.getTimeRentInMinutes(transaction));
        return new Response<>(ati, TransactionResponseMessage.SUCCESSFUL);
    }

    public Response<?> createTransaction(Integer customerId, Integer bikeId){
        ResponseMessage validateMessage = CustomerValidation.validate(customerId);
        if (validateMessage != CustomerResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        validateMessage = TransactionValidation.validateCreation(customerId);
        if (validateMessage != TransactionResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        validateMessage = BikeValidation.validate(bikeId);
        if (validateMessage != BikeResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        int status = transactionLayer.createTransaction(customerId, bikeId);
        if (status == -1) return new Response<>(null, TransactionResponseMessage.CAN_NOT_CREATE_TRANSACTION);
        return new Response<>(null, TransactionResponseMessage.SUCCESSFUL);
    }

    @AllArgsConstructor
    public static class ActiveTransactionInfo {
        Transaction transaction;
        Long currentPay;
        Long timeRent;

        public String convertToString() {
            return "ActiveTransactionInfo{" +
                    "transaction=" + transaction +
                    ", currentPay=" + currentPay +
                    ", timeRent=" + timeRent +
                    '}';
        }
    }
}
