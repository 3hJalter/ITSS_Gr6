package validation;

import database.entityLayer.TransactionLayer;
import entity.Transaction;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.TransactionResponseMessage;

public class TransactionValidation {
    private static boolean hasActiveTransaction(Integer customerId) {
        for (Transaction transaction : TransactionLayer.getInstance().getTransactionList()) {
            if (transaction.getCustomer().getCustomerId().equals(customerId)
                    && (transaction.getStatus().equals("active")
                    || transaction.getStatus().equals("paused"))) return true;
        }
        return false;
    }

    private static boolean isExist(Integer id) {
        for (Transaction transaction : TransactionLayer.getInstance().getTransactionList()) {
            if (transaction.getTransactionId().equals(id)) return true;
        }
        return false;
    }

    private static boolean isId(Integer id) {
        return id != null;
    }

    public static ResponseMessage validate(Integer id) {
        if (!isId(id)) return TransactionResponseMessage.TRANSACTION_ID_IS_INVALID;
        if (!isExist(id)) return TransactionResponseMessage.TRANSACTION_NOT_EXIST;
        return TransactionResponseMessage.SUCCESSFUL;
    }

    public static ResponseMessage validateCreation(Integer customerId) {
        if (hasActiveTransaction(customerId)) return TransactionResponseMessage.CUSTOMER_ALREADY_RENT_BIKE;
        return TransactionResponseMessage.SUCCESSFUL;
    }
}
