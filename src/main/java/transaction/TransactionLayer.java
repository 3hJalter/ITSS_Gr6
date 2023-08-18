package transaction;

import bike.BikeLayer;
import bike.bikeEntity.Bike;
import customer.Customer;
import customer.CustomerLayer;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JsonFunction;
import utils.PriceMethod;
import utils.database.BaseLayer;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the layer responsible for managing transactions.
 * It interacts with the database to retrieve and manipulate transaction data.
 */
public class TransactionLayer extends BaseLayer {

    private static TransactionLayer instance;
    JSONArray jsonArray;

    /**
     * Private constructor that initializes the TransactionLayer instance by fetching transaction data
     * from the database and converting it to JSON.
     */
    private TransactionLayer() {
        setJsonArray();
    }

    /**
     * Get the instance of TransactionLayer.
     *
     * @return The instance of TransactionLayer
     */
    public static TransactionLayer getInstance() {
        if (instance == null) {
            instance = new TransactionLayer();
        }
        return instance;
    }

    /**
     * Sets the JSON array with credit card data retrieved from the database.
     */
    private void setJsonArray() {
        try {
            String sqlQuery = "SELECT * FROM transaction";
            ResultSet resultSet = databaseConnection.getData(sqlQuery);
            jsonArray = JsonFunction.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
    }

    /**
     * Get a list of all transactions.
     *
     * @return A list of Transaction objects
     */
    public List<Transaction> getTransactionList() {
        return getTransactionFromJSON();
    }

    /**
     * Get a transaction by its ID.
     *
     * @param id The ID of the transaction
     * @return The Transaction object if found, otherwise null
     */
    public Transaction getTransactionById(Integer id) {
        if (id == null) return null;
        for (Transaction transaction : getTransactionFromJSON()) {
            if (transaction.getTransactionId().equals(id)) return transaction;
        }
        return null;
    }

    /**
     * Get an active or paused transaction associated with a customer.
     *
     * @param customerId The ID of the customer
     * @return The active or paused Transaction object if found, otherwise null
     */
    public Transaction getActiveTransactionByCustomerId(Integer customerId) {
        if (customerId == null) return null;
        for (Transaction transaction : getTransactionFromJSON()) {
            if (transaction.getCustomer().getCustomerId().equals(customerId)
                    && (transaction.getStatus().equals("active")
                    || transaction.getStatus().equals("paused"))) return transaction;
        }
        return null;
    }

    /**
     * Extract a list of Transaction objects from the stored JSON data.
     *
     * @return A list of Transaction objects parsed from the JSON data.
     */
    private List<Transaction> getTransactionFromJSON() {
        List<Transaction> transactionList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject transactionJson = jsonArray.getJSONObject(i);
                Customer customer = CustomerLayer.getInstance().getCustomerById(transactionJson.getInt("customer_id"));
                Bike bike = BikeLayer.getInstance().getBikeById(transactionJson.getInt("bike_id"));
                Timestamp createAt = Timestamp.valueOf(transactionJson.getString("create_at"));
                Timestamp lastPause = Timestamp.valueOf(transactionJson.getString("last_pause"));
                Transaction transaction = new Transaction(transactionJson.getInt("transaction_id"),
                        customer,
                        createAt,
                        transactionJson.getLong("deposit"),
                        bike,
                        transactionJson.getString("status"),
                        transactionJson.getString("transaction_type"),
                        transactionJson.getInt("minute_used"),
                        lastPause);
                transactionList.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    /**
     * Create a new transaction.
     *
     * @param customerId     The ID of the customer associated with the transaction
     * @param bikeId         The ID of the bike associated with the transaction
     * @param transactionType The type of the transaction
     * @return The generated ID of the created transaction, or -1 if creation fails
     */
    public int createTransaction(Integer customerId, Integer bikeId, String transactionType) {
        try {
            databaseConnection.getConnection().setAutoCommit(false);
            long deposit = PriceMethod.getDeposit(bikeId);
            String sqlQuery = "INSERT INTO transaction (customer_id, create_at, deposit, " +
                    "bike_id, status, transaction_type, minute_used, last_pause)\n"
                    + "VALUES ("
                    + customerId
                    + ", CURRENT_TIMESTAMP"
                    + ", "
                    + deposit
                    + ", " + bikeId
                    + ", 'active'"
                    + ", '" + transactionType + "'"
                    + ", " + 0
                    + ", CURRENT_TIMESTAMP"
                    + ");";
            int generatedId = databaseConnection.insertData(sqlQuery);
            databaseConnection.getConnection().commit();
            databaseConnection.getConnection().setAutoCommit(true);

            return generatedId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            setJsonArray();
        }
    }

    /**
     * Set a transaction to inactive status.
     *
     * @param transaction The Transaction object to be set as inactive
     */
    public void setTransactionToInactive(Transaction transaction) {
        if (transaction == null) return;
        try {
            databaseConnection.getConnection().setAutoCommit(false);
            String sqlQuery = "UPDATE transaction \n"
                    + "SET status = 'inactive' \n"
                    + "WHERE transaction_id = " + transaction.getTransactionId()
                    + ";";
            databaseConnection.updateData(sqlQuery);
            databaseConnection.getConnection().commit();
            databaseConnection.getConnection().setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            setJsonArray();
        }
    }

    /**
     * Pause a transaction.
     *
     * @param transaction The Transaction object to be paused
     */
    public void pauseTransaction(Transaction transaction) {
        if (transaction == null) return;
        try {
            long minuteUsed = PriceMethod.getActiveTimeRent(transaction);
            databaseConnection.getConnection().setAutoCommit(false);
            String sqlQuery = "UPDATE transaction \n"
                    + "SET status = 'paused'"
                    + ", minute_used = " + minuteUsed + " \n"
                    + "WHERE transaction_id = " + transaction.getTransactionId()
                    + ";";
            databaseConnection.updateData(sqlQuery);
            databaseConnection.getConnection().commit();
            databaseConnection.getConnection().setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            setJsonArray();
        }
    }

    /**
     * Unpause a paused transaction.
     *
     * @param transaction The Transaction object to be resumed
     */
    public void unPauseTransaction(Transaction transaction) {
        if (transaction == null) return;
        try {
            databaseConnection.getConnection().setAutoCommit(false);
            String sqlQuery = "UPDATE transaction \n"
                    + "SET status = 'active'"
                    + ", last_pause = CURRENT_TIMESTAMP" + "\n"
                    + "WHERE transaction_id = " + transaction.getTransactionId()
                    + ";";
            databaseConnection.updateData(sqlQuery);
            databaseConnection.getConnection().commit();
            databaseConnection.getConnection().setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            setJsonArray();
        }
    }
}
