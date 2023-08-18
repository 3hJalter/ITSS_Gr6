package subsystem.interbank.creditCard;

import java.sql.Timestamp;

/**
 * The CreditCard class represents a credit card with various attributes such as cardholder name,
 * card number, balance, expiration date, and security code.
 * It provides methods to access and modify credit card information.
 */
public class CreditCard {
    private Integer creditCardId;
    private String cardholderName;
    private String cardNumber;
    private String issuingBank;
    private Double balance;
    private Timestamp expirationDate;
    private String securityCode;

    /**
     * Constructs a new CreditCard instance with the given details.
     *
     * @param creditCardId The unique identifier for the credit card.
     * @param cardholderName The name of the cardholder.
     * @param cardNumber The credit card number.
     * @param issuingBank The bank that issued the credit card.
     * @param balance The current balance on the credit card.
     * @param expirationDate The expiration date of the credit card.
     * @param securityCode The security code of the credit card.
     */
    public CreditCard(Integer creditCardId, String cardholderName, String cardNumber,
                      String issuingBank, Double balance, Timestamp expirationDate, String securityCode) {
        this.creditCardId = creditCardId;
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.issuingBank = issuingBank;
        this.balance = balance;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
    }

    /**
     * Retrieves the unique identifier of the credit card.
     *
     * @return The credit card's unique identifier.
     */
    public Integer getCreditCardId() {
        return creditCardId;
    }

    /**
     * Sets the unique identifier of the credit card.
     *
     * @param creditCardId The new unique identifier to set for the credit card.
     */
    public void setCreditCardId(Integer creditCardId) {
        this.creditCardId = creditCardId;
    }

    /**
     * Retrieves the name of the cardholder.
     *
     * @return The name of the cardholder.
     */
    public String getCardholderName() {
        return cardholderName;
    }

    /**
     * Sets the name of the cardholder.
     *
     * @param cardholderName The new name to set for the cardholder.
     */
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    /**
     * Retrieves the credit card number.
     *
     * @return The credit card number.
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the credit card number.
     *
     * @param cardNumber The new credit card number to set.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Retrieves the bank that issued the credit card.
     *
     * @return The issuing bank of the credit card.
     */
    public String getIssuingBank() {
        return issuingBank;
    }

    /**
     * Sets the bank that issued the credit card.
     *
     * @param issuingBank The new issuing bank to set.
     */
    public void setIssuingBank(String issuingBank) {
        this.issuingBank = issuingBank;
    }

    /**
     * Retrieves the current balance on the credit card.
     *
     * @return The balance on the credit card.
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * Sets the current balance on the credit card.
     *
     * @param balance The new balance to set for the credit card.
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * Retrieves the expiration date of the credit card.
     *
     * @return The expiration date of the credit card.
     */
    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date of the credit card.
     *
     * @param expirationDate The new expiration date to set for the credit card.
     */
    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Retrieves the security code of the credit card.
     *
     * @return The security code of the credit card.
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * Sets the security code of the credit card.
     *
     * @param securityCode The new security code to set for the credit card.
     */
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
