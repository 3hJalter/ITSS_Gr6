package database.entityLayer;

import entity.Customer;
import entity.Invoice;
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

public class InvoiceLayer extends BaseLayer {
    private static InvoiceLayer instance;
    JSONArray jsonArray;

    private InvoiceLayer() {
        SetJsonArray();
    }

    public static InvoiceLayer getInstance() {
        if (instance == null) {
            instance = new InvoiceLayer();
        }
        return instance;
    }

    private void SetJsonArray() {
        try {
            String sqlQuery = "SELECT * FROM invoice";
            ResultSet resultSet = databaseConnection.getData(sqlQuery);
            jsonArray = General.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
    }

    public List<Invoice> getInvoiceList() {
        return getInvoiceFromJSON();
    }

    public Invoice getInvoiceById(Integer id) {
        if (id == null) return null;
        for (Invoice invoice : getInvoiceFromJSON()) {
            if (invoice.getInvoiceId().equals(id)) return invoice;
        }
        return null;
    }

    public List<Invoice> getInvoiceByCustomerId(Integer customerId) {
        if (customerId == null) return null;
        List<Invoice> invoiceList = new ArrayList<>();
        for (Invoice invoice : getInvoiceFromJSON()) {
            if (invoice.getCustomer().getCustomerId().equals(customerId)) invoiceList.add(invoice);
        }
        return invoiceList;
    }

    private List<Invoice> getInvoiceFromJSON() {
        List<Invoice> invoiceList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject invoiceJson = jsonArray.getJSONObject(i);
                Transaction transaction = TransactionLayer.getInstance().getTransactionById(
                        invoiceJson.getInt("transaction_id"));
                Customer customer = CustomerLayer.getInstance().getCustomerById(
                        invoiceJson.getInt("customer_id"));
                Bike bike = BikeLayer.getInstance().getBikeById(invoiceJson.getInt(
                        "bike_id"));
                Timestamp startRent = Timestamp.valueOf(invoiceJson.getString(
                        "start_rent"));
                Timestamp endRent = Timestamp.valueOf(invoiceJson.getString("end_rent"));
                Invoice invoice = new Invoice(invoiceJson.getInt("invoice_id"),
                        transaction,
                        customer,
                        startRent,
                        endRent,
                        invoiceJson.getLong("price"),
                        bike);
                invoiceList.add(invoice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceList;
    }

    public int createInvoice(Transaction transaction) {
        try {
            databaseConnection.getConnection().setAutoCommit(false);
            long price = PriceMethod.getTotalPrice(transaction);
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
