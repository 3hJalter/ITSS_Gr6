package subsystem.interbank.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
    private Integer creditCardId;
    private String cardholderName;
    private String cardNumber;
    private String issuingBank;
    private Double balance;
    private Timestamp expirationDate;
    private String securityCode;
}
