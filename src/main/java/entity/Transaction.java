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
public class Transaction {
    private Integer transactionId;
    private Customer customer;
    private Timestamp createdAt;
    private Long deposit;
    private Bike bike;
    private String status;

    public String convertToString(){
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", customer=" + customer +
                ", createdAt=" + createdAt +
                ", deposit=" + deposit +
                ", bike=" + bike +
                ", status=" + status +
                '}';
    }
}
