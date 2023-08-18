package customer;

import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.CustomerResponseMessage;

/**
 * The CustomerValidation class provides methods for validating customer-related operations.
 * It includes methods to check the existence and validity of customer IDs.
 */
public class CustomerValidation {

    /**
     * Checks if a customer with the given ID exists in the customer list.
     *
     * @param id The ID of the customer to check for existence.
     * @return {@code true} if a customer with the specified ID exists, {@code false} otherwise.
     */
    private static boolean isExist(Integer id) {
        for (Customer customer : CustomerLayer.getInstance().getCustomerList()) {
            if (customer.getCustomerId().equals(id)) return true;
        }
        return false;
    }

    /**
     * Checks if the provided customer ID is valid (not null).
     *
     * @param id The ID to be checked for validity.
     * @return {@code true} if the customer ID is valid (not null), {@code false} otherwise.
     */
    private static boolean isId(Integer id) {
        return id != null;
    }

    /**
     * Validates a customer ID by checking its existence and validity.
     *
     * @param id The ID of the customer to be validated.
     * @return A ResponseMessage indicating the validation result. Possible values:
     *         - {@link CustomerResponseMessage#SUCCESSFUL} if the ID is valid and the customer exists.
     *         - {@link CustomerResponseMessage#CUSTOMER_ID_IS_INVALID} if the ID is invalid.
     *         - {@link CustomerResponseMessage#CUSTOMER_NOT_EXIST} if the customer does not exist.
     */
    public static ResponseMessage validate(Integer id) {
        if (!isId(id)) return CustomerResponseMessage.CUSTOMER_ID_IS_INVALID;
        if (!isExist(id)) return CustomerResponseMessage.CUSTOMER_NOT_EXIST;
        return CustomerResponseMessage.SUCCESSFUL;
    }
}
