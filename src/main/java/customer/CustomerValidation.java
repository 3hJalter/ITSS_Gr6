package customer;

import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.CustomerResponseMessage;

public class CustomerValidation {
    private static boolean isExist(Integer id) {
        for (Customer customer : CustomerLayer.getInstance().getCustomerList()) {
            if (customer.getCustomerId().equals(id)) return true;
        }
        return false;
    }

    private static boolean isId(Integer id) {
        return id != null;
    }

    public static ResponseMessage validate(Integer id) {
        if (!isId(id)) return CustomerResponseMessage.CUSTOMER_ID_IS_INVALID;
        if (!isExist(id)) return CustomerResponseMessage.CUSTOMER_NOT_EXIST;
        return CustomerResponseMessage.SUCCESSFUL;
    }
}
