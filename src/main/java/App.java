import controller.InvoiceController;
import controller.TransactionController;
import database.entityLayer.InvoiceLayer;
import entity.Invoice;
import entity.Transaction;
import utils.General;
import utils.response.Response;
import utils.response.responseMessageImpl.InvoiceResponseMessage;
import utils.response.responseMessageImpl.TransactionResponseMessage;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Test");
/*        Response<?> response4 = InvoiceController.getInstance().createInvoice(4, 1);
        if (response4.getMessage().equals(InvoiceResponseMessage.SUCCESSFUL.getMessage())) {
            System.out.println("Create Invoice success");
        } else {
            System.out.println(response4.getMessage());
        }
        System.out.println("----------- Read All Invoice -------------");
        List<Invoice> invoiceList = InvoiceLayer.getInstance().getInvoiceList();
        for (Invoice invoice : invoiceList) {
            System.out.println(invoice.convertToString());
        }*/
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
        System.out.println("----------- Read All JSON -------------");
        String responseJSON = General.convertToJson(response);
        System.out.println(responseJSON);
//
//        System.out.println("----------- Create -------------");
//        Response<?> response1 = TransactionController.getInstance().createTransaction(1, 2);
//        if (response1.getMessage().equals(TransactionResponseMessage.SUCCESSFUL.getMessage())) {
//            System.out.println("Create transaction success");
//        } else {
//            System.out.println(response1.getMessage());
//        }
//        System.out.println("----------- Read Active Transaction -------------");
//        Response<TransactionController.ActiveTransaction> response2 = TransactionController.getInstance().getActiveTransactionByCustomerId(1);
//        if (response2.getMessage().equals(TransactionResponseMessage.SUCCESSFUL.getMessage())) {
//            TransactionController.ActiveTransaction transaction = response2.getObject();
//            System.out.println(transaction.convertToString());
//        } else {
//            System.out.println(response2.getMessage());
//        }
//        System.out.println("----------- Read All Again-------------");
//        Response<List<Transaction>> response3 = TransactionController.getInstance().getTransactionList();
//        if (response3.getMessage().equals(TransactionResponseMessage.SUCCESSFUL.getMessage())) {
//            List<Transaction> transactionList = response3.getObject();
//            for (Transaction transaction:
//                    transactionList) {
//                System.out.println(transaction.convertToString());
//            }
//        } else {
//            System.out.println(response3.getMessage());
//        }
    }
}
