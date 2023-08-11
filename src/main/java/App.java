import controller.CategoryController;
import controller.DockController;
import database.entityLayer.TransactionLayer;
import entity.Category;
import entity.Dock;
import entity.Transaction;
import org.json.JSONException;
import utils.response.Response;

import javax.print.Doc;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws SQLException, JSONException {
        System.out.println("Test");
//        List<Transaction> transactionList = TransactionLayer.getInstance().getTransactionList();
//        for (Transaction transaction : transactionList) {
//            System.out.println(transaction.convertToTransaction());
//        }
//        TransactionLayer.getInstance().createTransaction(
//                1, 100000L, 1
//        );
//        System.out.println("------------------------");
//        transactionList = TransactionLayer.getInstance().getTransactionList();
//        for (Transaction transaction : transactionList) {
//            System.out.println(transaction.convertToTransaction());
//        }
       Response<List<Category>> response = CategoryController.getInstance().getCategoryList();
       List<Category> docks = response.getObject();
        for (Category dock:docks) {
            System.out.println(dock.convertToString());
        }
    }
}
