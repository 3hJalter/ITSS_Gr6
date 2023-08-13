package controller;

import database.entityLayer.BikeLayer;
import database.entityLayer.InvoiceLayer;
import database.entityLayer.TransactionLayer;
import entity.Invoice;
import entity.Transaction;
import utils.PriceMethod;
import utils.response.Response;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.DockResponseMessage;
import utils.response.responseMessageImpl.InvoiceResponseMessage;
import utils.response.responseMessageImpl.TransactionResponseMessage;
import validation.CustomerValidation;
import validation.DockValidation;
import validation.InvoiceValidation;
import validation.TransactionValidation;

import java.util.List;

public class InvoiceController {
    private static InvoiceController instance;
    private static InvoiceLayer invoiceLayer;

    InvoiceController() {
        invoiceLayer = InvoiceLayer.getInstance();
    }

    public static InvoiceController getInstance() {
        if (instance == null) {
            instance = new InvoiceController();
        }
        return instance;
    }

    public Response<List<Invoice>> getInvoiceList() {
        List<Invoice> invoiceList = invoiceLayer.getInvoiceList();
        return new Response<>(invoiceList, InvoiceResponseMessage.SUCCESSFUL);
    }

    public Response<Invoice> getInvoiceById(Integer id) {
        ResponseMessage validateMessage = InvoiceValidation.validate(id);
        if (validateMessage != InvoiceResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Invoice invoice = invoiceLayer.getInvoiceById(id);
        return new Response<>(invoice, InvoiceResponseMessage.SUCCESSFUL);
    }

    public Response<List<Invoice>> getInvoiceByCustomerId(Integer customerId) {
        ResponseMessage validateMessage = CustomerValidation.validate(customerId);
        if (validateMessage != DockResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        List<Invoice> invoiceList = invoiceLayer.getInvoiceByCustomerId(customerId);
        return new Response<>(invoiceList, InvoiceResponseMessage.SUCCESSFUL);
    }

    public Response<?> createInvoice(Integer transactionId, Integer dockId) {
        // Invoice without credit card, need modify when have interbank subsystem
        ResponseMessage validateMessage = DockValidation.validate(dockId);
        if (validateMessage != DockResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        validateMessage = TransactionValidation.validate(transactionId);
        if (validateMessage != TransactionResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Transaction transaction = TransactionLayer.getInstance().getTransactionById(transactionId);
        validateMessage = InvoiceValidation.validateCreation(transaction);
        if (validateMessage != InvoiceResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        int newInvoiceId = invoiceLayer.createInvoice(transaction);
        if (newInvoiceId == -1) return new Response<>(null, InvoiceResponseMessage.CAN_NOT_CREATE_INVOICE);
        // Modify if you need return deposit logic
        System.out.println("Return deposit with value = "
                + PriceMethod.returnDeposit(transaction));
        //
        TransactionLayer.getInstance().setTransactionToInactive(transaction);
        BikeLayer.getInstance().returnInvoiceBikeToDockId(newInvoiceId, dockId);
        return new Response<>(null, InvoiceResponseMessage.SUCCESSFUL);
    }
}
