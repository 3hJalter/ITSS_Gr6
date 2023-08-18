package invoice;

import bike.bikeEntity.Bike;
import customer.Customer;
import transaction.Transaction;

import java.sql.Timestamp;

/**
 * The Invoice class represents an invoice associated with a rental transaction for a bike.
 * It contains information about the transaction, customer, rental duration, price, and the rented bike.
 */
public class Invoice {
    private Integer invoiceId;
    private Transaction transaction;
    private Customer customer;
    private Timestamp startRent;
    private Timestamp endRent;
    private Long price;
    private Bike bike;

    /**
     * Constructs a new Invoice instance with the provided details.
     *
     * @param invoiceId The unique identifier for the invoice.
     * @param transaction The associated transaction for the rental.
     * @param customer The customer associated with the invoice.
     * @param startRent The timestamp indicating the start of the rental period.
     * @param endRent The timestamp indicating the end of the rental period.
     * @param price The price of the rental.
     * @param bike The bike that was rented.
     */
    public Invoice(Integer invoiceId, Transaction transaction, Customer customer,
                   Timestamp startRent, Timestamp endRent, Long price, Bike bike) {
        this.invoiceId = invoiceId;
        this.transaction = transaction;
        this.customer = customer;
        this.startRent = startRent;
        this.endRent = endRent;
        this.price = price;
        this.bike = bike;
    }

    /**
     * Retrieves the unique identifier of the invoice.
     *
     * @return The invoice's unique identifier.
     */
    public Integer getInvoiceId() {
        return invoiceId;
    }

    /**
     * Sets the unique identifier of the invoice.
     *
     * @param invoiceId The new unique identifier to set for the invoice.
     */
    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Retrieves the associated transaction for the rental.
     *
     * @return The associated transaction.
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Sets the associated transaction for the rental.
     *
     * @param transaction The new transaction to associate with the invoice.
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * Retrieves the customer associated with the invoice.
     *
     * @return The customer associated with the invoice.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with the invoice.
     *
     * @param customer The new customer to associate with the invoice.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Retrieves the timestamp indicating the start of the rental period.
     *
     * @return The timestamp indicating the start of the rental.
     */
    public Timestamp getStartRent() {
        return startRent;
    }

    /**
     * Sets the timestamp indicating the start of the rental period.
     *
     * @param startRent The new timestamp to set as the start of the rental.
     */
    public void setStartRent(Timestamp startRent) {
        this.startRent = startRent;
    }

    /**
     * Retrieves the timestamp indicating the end of the rental period.
     *
     * @return The timestamp indicating the end of the rental.
     */
    public Timestamp getEndRent() {
        return endRent;
    }

    /**
     * Sets the timestamp indicating the end of the rental period.
     *
     * @param endRent The new timestamp to set as the end of the rental.
     */
    public void setEndRent(Timestamp endRent) {
        this.endRent = endRent;
    }

    /**
     * Retrieves the price of the rental.
     *
     * @return The price of the rental.
     */
    public Long getPrice() {
        return price;
    }

    /**
     * Sets the price of the rental.
     *
     * @param price The new price to set for the rental.
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * Retrieves the bike that was rented.
     *
     * @return The rented bike.
     */
    public Bike getBike() {
        return bike;
    }

    /**
     * Sets the bike that was rented.
     *
     * @param bike The new bike to associate with the rental.
     */
    public void setBike(Bike bike) {
        this.bike = bike;
    }
}
