package invoice;

import bike.BikeLayer;
import customer.CustomerValidation;
import dock.DockValidation;
import transaction.Transaction;
import transaction.TransactionLayer;
import transaction.TransactionValidation;
import utils.PriceMethod;
import utils.api.APIInterbankHandlers;
import utils.response.Response;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.DockResponseMessage;
import utils.response.responseMessageImpl.InvoiceResponseMessage;
import utils.response.responseMessageImpl.TransactionResponseMessage;

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

    public Response<?> createInvoice(Integer transactionId, Integer dockId
            , String cardNumber, String cardholderName
            , String issueBank, int month, int year, String securityCode) {
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

        // Call Interbank to pay rent fee here
        long price = transaction.getTransactionType().equals("24h")
                ? PriceMethod.get24hTotalPrice(transaction)
                : PriceMethod.getTotalPrice(transaction);
        String message = APIInterbankHandlers.payWithCard(cardNumber, cardholderName, issueBank, month, year,
                securityCode, price);
        if (!message.equals("Successful")) {
            return new Response<>(null, "501", message);
        }
        // End here

        int newInvoiceId = invoiceLayer.createInvoice(transaction);
        if (newInvoiceId == -1) {
            APIInterbankHandlers.receiveMoney(cardNumber, price);
            return new Response<>(null, InvoiceResponseMessage.CAN_NOT_CREATE_INVOICE);
            // Call Interbank to receive rent fee here
        }
        // Modify if you need return deposit logic
        Long deposit = PriceMethod.returnDeposit(transaction);
        System.out.println("Return deposit with value = " + deposit);
        // Call Interbank to receive deposit here
        APIInterbankHandlers.receiveMoney(cardNumber, deposit);
        TransactionLayer.getInstance().setTransactionToInactive(transaction);
        BikeLayer.getInstance().returnInvoiceBikeToDockId(newInvoiceId, dockId);
        return new Response<>(null, InvoiceResponseMessage.SUCCESSFUL);
    }
}
