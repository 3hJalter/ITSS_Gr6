package subsystem.interbank.creditCard;

import java.sql.Timestamp;

public class CreditCard {
    private Integer creditCardId;
    private String cardholderName;
    private String cardNumber;
    private String issuingBank;
    private Double balance;
    private Timestamp expirationDate;
    private String securityCode;

    public CreditCard(Integer creditCardId, String cardholderName, String cardNumber, String issuingBank, Double balance, Timestamp expirationDate, String securityCode) {
        this.creditCardId = creditCardId;
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.issuingBank = issuingBank;
        this.balance = balance;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
    }

    public Integer getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Integer creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getIssuingBank() {
        return issuingBank;
    }

    public void setIssuingBank(String issuingBank) {
        this.issuingBank = issuingBank;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
