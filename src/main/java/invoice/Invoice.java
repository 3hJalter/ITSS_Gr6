package invoice;

import bike.bikeEntity.Bike;
import customer.Customer;
import transaction.Transaction;

import java.sql.Timestamp;

public class Invoice {
    private Integer invoiceId;
    private Transaction transaction;
    private Customer customer;
    private Timestamp startRent;
    private Timestamp endRent;
    private Long price;
    private Bike bike;

    public Invoice(Integer invoiceId, Transaction transaction, Customer customer, Timestamp startRent, Timestamp endRent, Long price, Bike bike) {
        this.invoiceId = invoiceId;
        this.transaction = transaction;
        this.customer = customer;
        this.startRent = startRent;
        this.endRent = endRent;
        this.price = price;
        this.bike = bike;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Timestamp getStartRent() {
        return startRent;
    }

    public void setStartRent(Timestamp startRent) {
        this.startRent = startRent;
    }

    public Timestamp getEndRent() {
        return endRent;
    }

    public void setEndRent(Timestamp endRent) {
        this.endRent = endRent;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }
}
