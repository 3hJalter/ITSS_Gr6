package database.entityLayer;

import entity.Customer;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.General;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerLayer extends BaseLayer {
    private static CustomerLayer instance;
    JSONArray jsonArray;

    private CustomerLayer() {
        try {
            String sqlQuery = "SELECT * FROM customer";
            ResultSet resultSet = databaseConnection.getData(sqlQuery);
            jsonArray = General.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CustomerLayer getInstance() {
        if (instance == null) {
            instance = new CustomerLayer();
        }
        return instance;
    }

    public List<Customer> getCustomerList() {
        return getCustomerFromJSON();
    }

    public List<Customer> searchCustomer(String keyword) {
        List<Customer> list = new ArrayList<>();
        for (Customer customer : getCustomerFromJSON()) {
            if (customer.getUsername().contains(keyword)) list.add(customer);
        }
        return list;
    }

    public Customer getCustomerById(Integer id) {
        if (id == null) return null;
        for (Customer customer : getCustomerFromJSON()) {
            if (customer.getCustomerId().equals(id)) return customer;
        }
        return null;
    }

    private List<Customer> getCustomerFromJSON() {
        List<Customer> customerList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject customerJson = jsonArray.getJSONObject(i);
                Customer customer = new Customer(customerJson.getInt("customer_id"), customerJson.getString("username"));
                assert false;
                customerList.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }
}
