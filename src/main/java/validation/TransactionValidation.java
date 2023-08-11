package validation;

import database.entityLayer.TransactionLayer;
import entity.Transaction;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.TransactionResponseMessage;

public class TransactionValidation {
    private static boolean hasActiveTransaction(Integer customerId){
        for (Transaction transaction : TransactionLayer.getInstance().getTransactionList()) {
            if (transaction.getCustomer().getCustomerId().equals(customerId)
                    && transaction.getStatus().equals("active")) return true;
        }
        return false;
    }

    public static ResponseMessage validateCreation(Integer customerId) {
        if (hasActiveTransaction(customerId)) return TransactionResponseMessage.CUSTOMER_ALREADY_RENT_BIKE;
        return TransactionResponseMessage.SUCCESSFUL;
    }
}
