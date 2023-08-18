package invoice;

import bike.BikeLayer;
import bike.bikeEntity.Bike;
import customer.Customer;
import customer.CustomerLayer;
import org.json.JSONArray;
import org.json.JSONObject;
import transaction.Transaction;
import transaction.TransactionLayer;
import utils.JsonFunction;
import utils.PriceMethod;
import utils.database.BaseLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The InvoiceLayer class provides access to invoice data stored in a JSON format,
 * retrieved from a database table. It extends the BaseLayer class for database connection management.
 */
public class InvoiceLayer extends BaseLayer {
    private static InvoiceLayer instance;
    JSONArray jsonArray;

    /**
     * Private constructor that initializes the InvoiceLayer instance by fetching invoice data
     * from the database and converting it to JSON.
     */
    private InvoiceLayer() {
        SetJsonArray();
    }

    /**
     * Get the singleton instance of the InvoiceLayer class.
     *
     * @return The instance of the InvoiceLayer.
     */
    public static InvoiceLayer getInstance() {
        if (instance == null) {
            instance = new InvoiceLayer();
        }
        return instance;
    }

    /**
     * Sets the JSON array with invoice data retrieved from the database.
     */
    private void SetJsonArray() {
        try {
            String sqlQuery = "SELECT * FROM invoice";
            ResultSet resultSet = databaseConnection.getData(sqlQuery);
            jsonArray = JsonFunction.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
    }

    /**
     * Get a list of all invoices stored in the JSON data.
     *
     * @return A list of Invoice objects representing the invoices.
     */
    public List<Invoice> getInvoiceList() {
        return getInvoiceFromJSON();
    }

    /**
     * Get an invoice by its unique identifier.
     *
     * @param id The ID of the invoice to retrieve.
     * @return The Invoice object representing the invoice with the given ID,
     *         or null if no invoice with the provided ID is found.
     */
    public Invoice getInvoiceById(Integer id) {
        if (id == null) return null;
        for (Invoice invoice : getInvoiceFromJSON()) {
            if (invoice.getInvoiceId().equals(id)) return invoice;
        }
        return null;
    }

    /**
     * Get a list of invoices for a specific customer.
     *
     * @param customerId The ID of the customer for whom to retrieve invoices.
     * @return A list of Invoice objects associated with the provided customer ID.
     */
    public List<Invoice> getInvoiceByCustomerId(Integer customerId) {
        if (customerId == null) return null;
        List<Invoice> invoiceList = new ArrayList<>();
        for (Invoice invoice : getInvoiceFromJSON()) {
            if (invoice.getCustomer().getCustomerId().equals(customerId)) invoiceList.add(invoice);
        }
        return invoiceList;
    }

    /**
     * Extract a list of Invoice objects from the stored JSON data.
     *
     * @return A list of Invoice objects parsed from the JSON data.
     */
    private List<Invoice> getInvoiceFromJSON() {
        List<Invoice> invoiceList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject invoiceJson = jsonArray.getJSONObject(i);
                Transaction transaction = TransactionLayer.getInstance().getTransactionById(
                        invoiceJson.getInt("transaction_id"));
                Customer customer = CustomerLayer.getInstance().getCustomerById(
                        invoiceJson.getInt("customer_id"));
                Bike bike = BikeLayer.getInstance().getBikeById(invoiceJson.getInt("bike_id"));
                Timestamp startRent = Timestamp.valueOf(invoiceJson.getString("start_rent"));
                Timestamp endRent = Timestamp.valueOf(invoiceJson.getString("end_rent"));
                Invoice invoice = new Invoice(
                        invoiceJson.getInt("invoice_id"),
                        transaction,
                        customer,
                        startRent,
                        endRent,
                        invoiceJson.getLong("price"),
                        bike
                );
                invoiceList.add(invoice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceList;
    }

    /**
     * Create a new invoice for a given transaction.
     *
     * @param transaction The transaction for which to create an invoice.
     * @return The generated ID of the created invoice, or -1 if the creation fails.
     */
    public int createInvoice(Transaction transaction) {
        try {
            databaseConnection.getConnection().setAutoCommit(false);
            long price = transaction.getTransactionType().equals("24h")
                    ? PriceMethod.get24hTotalPrice(transaction)
                    : PriceMethod.getTotalPrice(transaction);
            String sqlQuery = "INSERT INTO invoice (customer_id, transaction_id, start_rent, end_rent, " +
                    "price, bike_id)\n"
                    + "VALUES ("
                    + transaction.getCustomer().getCustomerId()
                    + ", " + transaction.getTransactionId()
                    + ", '" + transaction.getCreatedAt() + "'"
                    + ", CURRENT_TIMESTAMP"
                    + ", " + price
                    + ", " + transaction.getBike().getBikeId()
                    + ");";
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
