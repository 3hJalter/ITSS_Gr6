package customer;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.JsonFunction;
import utils.database.BaseLayer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * The CustomerLayer class provides access to customer data stored in a JSON format,
 * retrieved from a database table. It extends the BaseLayer class for database connection management.
 */
public class CustomerLayer extends BaseLayer {
    private static CustomerLayer instance;
    JSONArray jsonArray;

    /**
     * Private constructor that initializes the CustomerLayer instance by fetching customer data
     * from the database and converting it to JSON.
     */
    private CustomerLayer() {
        try {
            String sqlQuery = "SELECT * FROM customer";
            ResultSet resultSet = databaseConnection.getData(sqlQuery);
            jsonArray = JsonFunction.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
    }

    /**
     * Get the singleton instance of the CustomerLayer class.
     *
     * @return The instance of the CustomerLayer.
     */
    public static CustomerLayer getInstance() {
        if (instance == null) {
            instance = new CustomerLayer();
        }
        return instance;
    }

    /**
     * Get a list of all customers stored in the JSON data.
     *
     * @return A list of Customer objects representing the customers.
     */
    public List<Customer> getCustomerList() {
        return getCustomerFromJSON();
    }

    /**
     * Get a customer by their unique identifier.
     *
     * @param id The ID of the customer to retrieve.
     * @return The Customer object representing the customer with the given ID,
     *         or null if no customer with the provided ID is found.
     */
    public Customer getCustomerById(Integer id) {
        if (id == null) return null;
        for (Customer customer : getCustomerFromJSON()) {
            if (customer.getCustomerId().equals(id)) return customer;
        }
        return null;
    }

    /**
     * Extract a list of Customer objects from the stored JSON data.
     *
     * @return A list of Customer objects parsed from the JSON data.
     */
    private List<Customer> getCustomerFromJSON() {
        List<Customer> customerList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject customerJson = jsonArray.getJSONObject(i);
                Customer customer = new Customer(
                        customerJson.getInt("customer_id"),
                        customerJson.getString("username")
                );

                customerList.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }
}
