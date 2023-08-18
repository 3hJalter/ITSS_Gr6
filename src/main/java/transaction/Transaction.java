package transaction;

import bike.bikeEntity.Bike;
import customer.Customer;

import java.sql.Timestamp;

/**
 * The Transaction class represents a transaction associated with bike rental.
 * It contains information about the customer, rental details, status, and transaction type.
 */
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

    /**
     * Constructs a new Transaction instance with the given details.
     *
     * @param transactionId The unique identifier for the transaction.
     * @param customer The customer associated with the transaction.
     * @param createdAt The timestamp indicating when the transaction was created.
     * @param deposit The deposit amount for the transaction.
     * @param bike The bike associated with the transaction.
     * @param status The status of the transaction (e.g., ongoing, completed, paused).
     * @param transactionType The type of the transaction (e.g., rental, return).
     * @param minuteUsed The number of minutes the bike was used in the transaction.
     * @param lastPause The timestamp indicating when the transaction was last paused (if applicable).
     */
    public Transaction(Integer transactionId, Customer customer, Timestamp createdAt,
                       Long deposit, Bike bike, String status, String transactionType,
                       Integer minuteUsed, Timestamp lastPause) {
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

    /**
     * Retrieves the unique identifier of the transaction.
     *
     * @return The transaction's unique identifier.
     */
    public Integer getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the unique identifier of the transaction.
     *
     * @param transactionId The new unique identifier to set for the transaction.
     */
    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Retrieves the customer associated with the transaction.
     *
     * @return The customer associated with the transaction.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with the transaction.
     *
     * @param customer The new customer to associate with the transaction.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Retrieves the timestamp indicating when the transaction was created.
     *
     * @return The timestamp indicating the creation time of the transaction.
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp indicating when the transaction was created.
     *
     * @param createdAt The new creation timestamp to set for the transaction.
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the deposit amount for the transaction.
     *
     * @return The deposit amount.
     */
    public Long getDeposit() {
        return deposit;
    }

    /**
     * Sets the deposit amount for the transaction.
     *
     * @param deposit The new deposit amount to set for the transaction.
     */
    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    /**
     * Retrieves the bike associated with the transaction.
     *
     * @return The bike associated with the transaction.
     */
    public Bike getBike() {
        return bike;
    }

    /**
     * Sets the bike associated with the transaction.
     *
     * @param bike The new bike to associate with the transaction.
     */
    public void setBike(Bike bike) {
        this.bike = bike;
    }

    /**
     * Retrieves the status of the transaction.
     *
     * @return The status of the transaction.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the transaction.
     *
     * @param status The new status to set for the transaction.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retrieves the type of the transaction.
     *
     * @return The type of the transaction.
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the type of the transaction.
     *
     * @param transactionType The new transaction type to set.
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Retrieves the number of minutes the bike was used in the transaction.
     *
     * @return The number of minutes used.
     */
    public Integer getMinuteUsed() {
        return minuteUsed;
    }

    /**
     * Sets the number of minutes the bike was used in the transaction.
     *
     * @param minuteUsed The new number of minutes used to set.
     */
    public void setMinuteUsed(Integer minuteUsed) {
        this.minuteUsed = minuteUsed;
    }

    /**
     * Retrieves the timestamp indicating when the transaction was last paused.
     *
     * @return The timestamp of the last pause.
     */
    public Timestamp getLastPause() {
        return lastPause;
    }

    /**
     * Sets the timestamp indicating when the transaction was last paused.
     *
     * @param lastPause The new timestamp of the last pause to set.
     */
    public void setLastPause(Timestamp lastPause) {
        this.lastPause = lastPause;
    }
}
