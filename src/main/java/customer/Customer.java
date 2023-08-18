package customer;

/**
 * The Customer class represents a customer entity with a unique customer ID and a username.
 * It provides methods to access and modify customer information.
 */
public class Customer {
    private Integer customerId;
    private String username;

    /**
     * Constructs a new Customer instance with the given customer ID and username.
     *
     * @param customerId The unique identifier for the customer.
     * @param username The username associated with the customer.
     */
    public Customer(Integer customerId, String username) {
        this.customerId = customerId;
        this.username = username;
    }

    /**
     * Retrieves the customer's unique ID.
     *
     * @return The customer's unique ID.
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer's unique ID.
     *
     * @param customerId The new unique ID to set for the customer.
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * Retrieves the customer's username.
     *
     * @return The customer's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the customer's username.
     *
     * @param username The new username to set for the customer.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
