package transaction;

import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.TransactionResponseMessage;

/**
 * The TransactionValidation class provides methods to validate transactions and their attributes.
 */
public class TransactionValidation {
    /**
     * Checks if there is an active or paused transaction associated with the provided customer ID.
     *
     * @param customerId The ID of the customer to check for active or paused transactions.
     * @return True if there is an active or paused transaction for the given customer ID, false otherwise.
     */
    private static boolean hasActiveTransaction(Integer customerId) {
        for (Transaction transaction : TransactionLayer.getInstance().getTransactionList()) {
            if (transaction.getCustomer().getCustomerId().equals(customerId)
                    && (transaction.getStatus().equals("active")
                    || transaction.getStatus().equals("paused"))) return true;
        }
        return false;
    }

    /**
     * Checks if a transaction with the given ID exists in the transaction list.
     *
     * @param id The ID of the transaction to check for existence.
     * @return True if a transaction with the given ID exists, false otherwise.
     */
    private static boolean isExist(Integer id) {
        for (Transaction transaction : TransactionLayer.getInstance().getTransactionList()) {
            if (transaction.getTransactionId().equals(id)) return true;
        }
        return false;
    }

    /**
     * Checks if the provided ID is valid.
     *
     * @param id The ID to check for validity.
     * @return True if the ID is not null, false otherwise.
     */
    private static boolean isId(Integer id) {
        return id != null;
    }

    /**
     * Validates a transaction based on its ID.
     *
     * @param id The ID of the transaction to validate.
     * @return A ResponseMessage indicating the validation result. Possible values:
     *         - TRANSACTION_ID_IS_INVALID: If the provided ID is null.
     *         - TRANSACTION_NOT_EXIST: If no transaction with the provided ID exists.
     *         - SUCCESSFUL: If the transaction ID is valid and exists.
     */
    public static ResponseMessage validate(Integer id) {
        if (!isId(id)) return TransactionResponseMessage.TRANSACTION_ID_IS_INVALID;
        if (!isExist(id)) return TransactionResponseMessage.TRANSACTION_NOT_EXIST;
        return TransactionResponseMessage.SUCCESSFUL;
    }

    /**
     * Validates the creation of a transaction for a specific customer.
     *
     * @param customerId The ID of the customer for whom the transaction is being created.
     * @return A ResponseMessage indicating the validation result. Possible values:
     *         - CUSTOMER_ALREADY_RENT_BIKE: If the customer already has an active or paused transaction.
     *         - SUCCESSFUL: If the customer is eligible to create a new transaction.
     */
    public static ResponseMessage validateCreation(Integer customerId) {
        if (hasActiveTransaction(customerId)) return TransactionResponseMessage.CUSTOMER_ALREADY_RENT_BIKE;
        return TransactionResponseMessage.SUCCESSFUL;
    }
}
