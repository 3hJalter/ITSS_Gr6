package entity;

import entity.bike.Bike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    private Integer invoiceId;
    private Customer customer;
    private Date startRent;
    private Date endRent;
    private Long price;
    private Bike bike;
}
