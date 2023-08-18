package invoice;

import transaction.Transaction;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.InvoiceResponseMessage;

/**
 * The InvoiceValidation class provides methods for validating invoice-related operations.
 * It includes methods to check the existence, validity of IDs, and status of transactions.
 */
public class InvoiceValidation {

    /**
     * Checks if a transaction is active or paused.
     *
     * @param transaction The transaction to check for active or paused status.
     * @return {@code true} if the transaction is active or paused, {@code false} otherwise.
     */
    private static boolean hasActiveTransaction(Transaction transaction) {
        return (transaction.getStatus().equals("active") || transaction.getStatus().equals("paused"));
    }

    /**
     * Checks if an invoice with the given ID exists in the invoice list.
     *
     * @param id The ID of the invoice to check for existence.
     * @return {@code true} if an invoice with the specified ID exists, {@code false} otherwise.
     */
    private static boolean isExist(Integer id) {
        for (Invoice invoice : InvoiceLayer.getInstance().getInvoiceList()) {
            if (invoice.getInvoiceId().equals(id)) return true;
        }
        return false;
    }

    /**
     * Checks if the provided invoice ID is valid (not null).
     *
     * @param id The ID to be checked for validity.
     * @return {@code true} if the invoice ID is valid (not null), {@code false} otherwise.
     */
    private static boolean isId(Integer id) {
        return id != null;
    }

    /**
     * Validates an invoice ID by checking its existence and validity.
     *
     * @param id The ID of the invoice to be validated.
     * @return A ResponseMessage indicating the validation result. Possible values:
     *         - {@link InvoiceResponseMessage#SUCCESSFUL} if the ID is valid and the invoice exists.
     *         - {@link InvoiceResponseMessage#INVOICE_ID_IS_INVALID} if the ID is invalid.
     *         - {@link InvoiceResponseMessage#INVOICE_NOT_EXIST} if the invoice does not exist.
     */
    public static ResponseMessage validate(Integer id) {
        if (!isId(id)) return InvoiceResponseMessage.INVOICE_ID_IS_INVALID;
        if (!isExist(id)) return InvoiceResponseMessage.INVOICE_NOT_EXIST;
        return InvoiceResponseMessage.SUCCESSFUL;
    }

    /**
     * Validates the creation of an invoice by checking the status of the associated transaction.
     *
     * @param transaction The transaction associated with the invoice creation.
     * @return A ResponseMessage indicating the validation result. Possible values:
     *         - {@link InvoiceResponseMessage#SUCCESSFUL} if the transaction is active or paused.
     *         - {@link InvoiceResponseMessage#TRANSACTION_IS_INACTIVE} if the transaction is not active or paused.
     */
    public static ResponseMessage validateCreation(Transaction transaction) {
        if (!hasActiveTransaction(transaction)) return InvoiceResponseMessage.TRANSACTION_IS_INACTIVE;
        return InvoiceResponseMessage.SUCCESSFUL;
    }
}
