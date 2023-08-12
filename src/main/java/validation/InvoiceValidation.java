package validation;

import database.entityLayer.InvoiceLayer;
import database.entityLayer.TransactionLayer;
import entity.Invoice;
import entity.Transaction;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.InvoiceResponseMessage;

public class InvoiceValidation {
    private static boolean hasActiveTransaction(Transaction transaction) {
        return transaction.getStatus().equals("active");
    }

    private static boolean isExist(Integer id){
        for (Invoice invoice : InvoiceLayer.getInstance().getInvoiceList()) {
            if (invoice.getInvoiceId().equals(id)) return true;
        }
        return false;
    }

    private static boolean isId(Integer id){
        return id != null;
    }

    public static ResponseMessage validate(Integer id){
        if (!isId(id)) return InvoiceResponseMessage.INVOICE_ID_IS_INVALID;
        if (!isExist(id)) return InvoiceResponseMessage.INVOICE_NOT_EXIST;
        return InvoiceResponseMessage.SUCCESSFUL;
    }

    public static ResponseMessage validateCreation(Transaction transaction) {
        if (!hasActiveTransaction(transaction)) return InvoiceResponseMessage.TRANSACTION_IS_INACTIVE;
        return InvoiceResponseMessage.SUCCESSFUL;
    }
}
