package database.entityLayer;

import entity.Customer;
import entity.Transaction;
import entity.bike.Bike;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.General;
import utils.PriceMethod;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionLayer extends BaseLayer {
    private static TransactionLayer instance;
    JSONArray jsonArray;

    private TransactionLayer() {
        SetJsonArray();
    }

    public static TransactionLayer getInstance() {
        if (instance == null) {
            instance = new TransactionLayer();
        }
        return instance;
    }

    private void SetJsonArray() {
        try {
            String sqlQuery = "SELECT * FROM transaction";
            ResultSet resultSet = databaseConnection.getData(sqlQuery);
            jsonArray = General.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
    }

    public List<Transaction> getTransactionList() {
        return getTransactionFromJSON();
    }

    public Transaction getActiveTransactionByCustomerId(Integer customerId) {
        if (customerId == null) return null;
        for (Transaction transaction : getTransactionFromJSON()) {
            if (transaction.getCustomer().getCustomerId().equals(customerId)
                    && transaction.getStatus().equals("active")) return transaction;
        }
        return null;
    }

    private List<Transaction> getTransactionFromJSON() {
        List<Transaction> transactionList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject transactionJson = jsonArray.getJSONObject(i);
                Customer customer = CustomerLayer.getInstance().getCustomerById(transactionJson.getInt("customer_id"));
                Bike bike = BikeLayer.getInstance().getBikeById(transactionJson.getInt("bike_id"));
                Timestamp createAt = Timestamp.valueOf(transactionJson.getString("create_at"));
                Transaction transaction = new Transaction(transactionJson.getInt("transaction_id"),
                        customer,
                        createAt,
                        transactionJson.getLong("deposit"),
                        bike,
                        transactionJson.getString("status"));
                assert false;
                transactionList.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    public int createTransaction(Integer customerId, Integer bikeId) {
        try {
            databaseConnection.getConnection().setAutoCommit(false);
            long deposit = PriceMethod.getDeposit(bikeId);
            String sqlQuery = "INSERT INTO transaction (customer_id, create_at, deposit, bike_id, status)\n"
                    + "VALUES ("
                    + customerId
                    + ", CURRENT_TIMESTAMP"
                    + ", "
                    + deposit
                    + ", " + bikeId
                    + ", 'active');";
            int generatedId = databaseConnection.insertData(sqlQuery);
            databaseConnection.getConnection().commit();
            SetJsonArray();
            return generatedId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                databaseConnection.getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            databaseConnection.closeConnection();
        }
    }
}