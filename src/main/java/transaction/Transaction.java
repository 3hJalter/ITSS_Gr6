package transaction;

import bike.bikeEntity.Bike;
import customer.Customer;

import java.sql.Timestamp;
public class Transaction {
    private Integer transactionId;
    private Customer customer;
    private Timestamp createdAt;
    private Long deposit;
    private Bike bike;
    private String status;
    private String transactionType;
    private Integer minuteUsed;
    private Timestamp lastPause;

    public Transaction(Integer transactionId, Customer customer, Timestamp createdAt, Long deposit, Bike bike, String status, String transactionType, Integer minuteUsed, Timestamp lastPause) {
        this.transactionId = transactionId;
        this.customer = customer;
        this.createdAt = createdAt;
        this.deposit = deposit;
        this.bike = bike;
        this.status = status;
        this.transactionType = transactionType;
        this.minuteUsed = minuteUsed;
        this.lastPause = lastPause;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getMinuteUsed() {
        return minuteUsed;
    }

    public void setMinuteUsed(Integer minuteUsed) {
        this.minuteUsed = minuteUsed;
    }

    public Timestamp getLastPause() {
        return lastPause;
    }

    public void setLastPause(Timestamp lastPause) {
        this.lastPause = lastPause;
    }
}
