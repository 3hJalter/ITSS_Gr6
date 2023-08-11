import controller.TransactionController;
import database.entityLayer.TransactionLayer;
import entity.Transaction;
import utils.response.Response;
import utils.response.responseMessageImpl.TransactionResponseMessage;

import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws SQLException {
        System.out.println("Test");
        System.out.println("----------- Read All -------------");
        Response<List<Transaction>> response = TransactionController.getInstance().getTransactionList();
        if (response.getMessage().equals(TransactionResponseMessage.SUCCESSFUL.getMessage())) {
            List<Transaction> transactionList = response.getObject();
            for (Transaction transaction:
                 transactionList) {
                System.out.println(transaction.convertToString());
            }
        } else {
            System.out.println(response.getMessage());
        }
        System.out.println("----------- Create -------------");
        Response<?> response1 = TransactionController.getInstance().createTransaction(1, 2);
        if (response1.getMessage().equals(TransactionResponseMessage.SUCCESSFUL.getMessage())) {
            System.out.println("Create transaction success");
        } else {
            System.out.println(response1.getMessage());
        }
        System.out.println("----------- Read Active Transaction -------------");
        Response<Transaction> response2 = TransactionController.getInstance().getActiveTransactionByCustomerId(1);
        if (response2.getMessage().equals(TransactionResponseMessage.SUCCESSFUL.getMessage())) {
            Transaction transaction = response2.getObject();
            System.out.println(transaction.convertToString());
        } else {
            System.out.println(response2.getMessage());
        }
        System.out.println("----------- Read All Again-------------");
        Response<List<Transaction>> response3 = TransactionController.getInstance().getTransactionList();
        if (response3.getMessage().equals(TransactionResponseMessage.SUCCESSFUL.getMessage())) {
            List<Transaction> transactionList = response3.getObject();
            for (Transaction transaction:
                    transactionList) {
                System.out.println(transaction.convertToString());
            }
        } else {
            System.out.println(response3.getMessage());
        }
    }
}
