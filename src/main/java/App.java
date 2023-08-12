import controller.TransactionController;
import entity.Transaction;
import utils.response.Response;
import utils.response.responseMessageImpl.TransactionResponseMessage;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Test");
        System.out.println("----------- Read Active Transaction -------------");
        Response<TransactionController.ActiveTransactionInfo> response2 = TransactionController.getInstance().getActiveTransactionByCustomerId(1);
        if (response2.getMessage().equals(TransactionResponseMessage.SUCCESSFUL.getMessage())) {
            TransactionController.ActiveTransactionInfo transaction = response2.getObject();
            System.out.println(transaction.convertToString());
        } else {
            System.out.println(response2.getMessage());
        }
    }
}
