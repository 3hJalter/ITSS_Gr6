package entity;

import entity.bike.Bike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    private Integer invoiceId;
    private Transaction transaction;
    private Customer customer;
    private Timestamp startRent;
    private Timestamp endRent;
    private Long price;
    private Bike bike;

    public String convertToString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", transaction=" + transaction +
                ", customer=" + customer +
                ", startRent=" + startRent +
                ", endRent=" + endRent +
                ", price=" + price +
                ", bike=" + bike +
                '}';
    }
}
