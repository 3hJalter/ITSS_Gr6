package controller;

import database.entityLayer.TransactionLayer;
import entity.Transaction;
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

    public Response<Transaction> getActiveTransactionByCustomerId(Integer customerId) {
        ResponseMessage validateMessage = CustomerValidation.validate(customerId);
        if (validateMessage != CustomerResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Transaction transaction = transactionLayer.getActiveTransactionByCustomerId(customerId);
        if (transaction == null) return new Response<>(null, TransactionResponseMessage.TRANSACTION_NOT_EXIST);
        return new Response<>(transaction, TransactionResponseMessage.SUCCESSFUL);
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

}
