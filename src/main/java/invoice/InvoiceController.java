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

/**
 * The InvoiceController class provides methods to manage and interact with invoices.
 * It handles various invoice-related operations such as creation, retrieval, and listing.
 */
public class InvoiceController {
    private static InvoiceController instance;
    private static InvoiceLayer invoiceLayer;

    /**
     * Private constructor initializes the InvoiceLayer instance.
     */
    InvoiceController() {
        invoiceLayer = InvoiceLayer.getInstance();
    }

    /**
     * Returns the instance of InvoiceController. If an instance doesn't exist, it creates one.
     *
     * @return The InvoiceController instance.
     */
    public static InvoiceController getInstance() {
        if (instance == null) {
            instance = new InvoiceController();
        }
        return instance;
    }

    /**
     * Retrieves a list of all invoices.
     *
     * @return A Response object containing the list of invoices and a response message.
     */
    public Response<List<Invoice>> getInvoiceList() {
        List<Invoice> invoiceList = invoiceLayer.getInvoiceList();
        return new Response<>(invoiceList, InvoiceResponseMessage.SUCCESSFUL);
    }

    /**
     * Retrieves an invoice by its unique identifier.
     *
     * @param id The identifier of the invoice to retrieve.
     * @return A Response object containing the retrieved invoice and a response message.
     */
    public Response<Invoice> getInvoiceById(Integer id) {
        ResponseMessage validateMessage = InvoiceValidation.validate(id);
        if (validateMessage != InvoiceResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        Invoice invoice = invoiceLayer.getInvoiceById(id);
        return new Response<>(invoice, InvoiceResponseMessage.SUCCESSFUL);
    }

    /**
     * Retrieves a list of invoices associated with a specific customer.
     *
     * @param customerId The identifier of the customer.
     * @return A Response object containing the list of invoices and a response message.
     */
    public Response<List<Invoice>> getInvoiceByCustomerId(Integer customerId) {
        ResponseMessage validateMessage = CustomerValidation.validate(customerId);
        if (validateMessage != DockResponseMessage.SUCCESSFUL)
            return new Response<>(null, validateMessage);
        List<Invoice> invoiceList = invoiceLayer.getInvoiceByCustomerId(customerId);
        return new Response<>(invoiceList, InvoiceResponseMessage.SUCCESSFUL);
    }

    /**
     * Creates an invoice for a given transaction and dock with credit card payment.
     *
     * @param transactionId  The identifier of the transaction associated with the invoice.
     * @param dockId         The identifier of the dock associated with the invoice.
     * @param cardNumber     The credit card number for payment.
     * @param cardholderName The name of the cardholder.
     * @param issueBank      The issuing bank of the credit card.
     * @param month          The expiry month of the credit card.
     * @param year           The expiry year of the credit card.
     * @param securityCode   The security code of the credit card.
     * @return A Response object with a success or error message.
     */
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
