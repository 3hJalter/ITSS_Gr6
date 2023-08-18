package customer;

public class Customer {
    private Integer customerId;
    private String username;

    public Customer(Integer customerId, String username) {
        this.customerId = customerId;
        this.username = username;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
