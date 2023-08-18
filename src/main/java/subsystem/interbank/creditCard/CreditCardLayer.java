package subsystem.interbank.creditCard;

import org.json.JSONArray;
import org.json.JSONObject;
import subsystem.interbank.database.InterbankPostgresConnection;
import utils.JsonFunction;
import utils.database.connection.IDatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The CreditCardLayer class provides access to credit card data stored in a JSON format,
 * retrieved from a database table. It facilitates operations related to credit card information,
 * including querying balances, changing balances, and retrieving credit card details.
 */
public class CreditCardLayer {
    private static IDatabaseConnection databaseConnection;
    private static CreditCardLayer instance;
    JSONArray jsonArray;

    /**
     * Private constructor that initializes the CreditCardLayer instance by fetching credit card data
     * from the database and converting it to JSON.
     */
    private CreditCardLayer() {
        if (databaseConnection == null)
            databaseConnection = InterbankPostgresConnection.getInstance();
        setJsonArray();
    }

    /**
     * Get the singleton instance of the CreditCardLayer class.
     *
     * @return The instance of the CreditCardLayer.
     */
    public static CreditCardLayer getInstance() {
        if (instance == null) {
            instance = new CreditCardLayer();
        }
        return instance;
    }

    /**
     * Sets the JSON array with credit card data retrieved from the database.
     */
    private void setJsonArray() {
        try {
            String sqlQuery = "SELECT * FROM credit_card";
            ResultSet resultSet = databaseConnection.getData(sqlQuery);
            jsonArray = JsonFunction.convertResultSetToJsonArray(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
    }

    /**
     * Get a list of all credit cards stored in the JSON data.
     *
     * @return A list of CreditCard objects representing the credit cards.
     */
    public List<CreditCard> getCreditCardList() {
        return getCreditCardFromJSON();
    }

    /**
     * Get a credit card by its card number.
     *
     * @param cardNumber The card number of the credit card to retrieve.
     * @return The CreditCard object representing the credit card with the given card number,
     *         or null if no credit card with the provided card number is found.
     */
    public CreditCard getCreditCardByCardNumber(String cardNumber) {
        if (cardNumber == null) return null;
        for (CreditCard creditCard : getCreditCardFromJSON()) {
            if (creditCard.getCardNumber().equals(cardNumber)) return creditCard;
        }
        return null;
    }

    /**
     * Get a credit card by its unique identifier.
     *
     * @param cardId The ID of the credit card to retrieve.
     * @return The CreditCard object representing the credit card with the given ID,
     *         or null if no credit card with the provided ID is found.
     */
    public CreditCard getCreditCardByCardId(Integer cardId) {
        if (cardId == null) return null;
        for (CreditCard creditCard : getCreditCardFromJSON()) {
            if (creditCard.getCreditCardId().equals(cardId)) return creditCard;
        }
        return null;
    }

    /**
     * Get the balance of a credit card by its card number.
     *
     * @param cardNumber The card number of the credit card for which to retrieve the balance.
     * @return The balance of the credit card with the given card number,
     *         or -1 if no credit card with the provided card number is found.
     */
    public Double getBalance(String cardNumber) {
        if (cardNumber == null) return -1D;
        CreditCard creditCard = getCreditCardByCardNumber(cardNumber);
        if (creditCard == null) return -1D;
        return creditCard.getBalance();
    }

    /**
     * Reset the balance of a credit card to a predefined value.
     *
     * @param cardNumber The card number of the credit card for which to reset the balance.
     * @return 0 if the balance reset is successful, -1 otherwise.
     */
    public int resetBalance(String cardNumber) {
        if (cardNumber == null) return -1;
        CreditCard card = getCreditCardByCardNumber(cardNumber);
        if (card == null) return -1;
        try {
            databaseConnection.getConnection().setAutoCommit(false);
            String sqlQuery = "UPDATE credit_card \n"
                    + "SET balance = 999999999 \n"
                    + "WHERE credit_card_id = " + card.getCreditCardId()
                    + ";";
            databaseConnection.updateData(sqlQuery);
            databaseConnection.getConnection().commit();
            databaseConnection.getConnection().setAutoCommit(true);
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            setJsonArray();
        }
    }

    /**
     * Change the balance of a credit card based on a transaction.
     *
     * @param card     The CreditCard object for which to change the balance.
     * @param price    The amount to change the balance by.
     * @param isPay    Whether the transaction is a payment (true) or a receive (false).
     * @return The ID of the credit card if the balance change is successful, -1 otherwise.
     */
    public Integer changeCardBalance(CreditCard card, Double price, boolean isPay) {
        if (isPay) {
            if (card.getBalance() < price) return -1;
            card.setBalance(card.getBalance() - price);
        } else card.setBalance(card.getBalance() + price);
        try {
            databaseConnection.getConnection().setAutoCommit(false);
            String sqlQuery = "UPDATE credit_card \n"
                    + "SET balance = " + card.getBalance() + " \n"
                    + "WHERE credit_card_id = " + card.getCreditCardId()
                    + ";";
            databaseConnection.updateData(sqlQuery);
            databaseConnection.getConnection().commit();
            databaseConnection.getConnection().setAutoCommit(true);
            return card.getCreditCardId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            setJsonArray();
        }
    }

    /**
     * Extract a list of CreditCard objects from the stored JSON data.
     *
     * @return A list of CreditCard objects parsed from the JSON data.
     */
    private List<CreditCard> getCreditCardFromJSON() {
        List<CreditCard> creditCardList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject creditCardJson = jsonArray.getJSONObject(i);
                Timestamp expirationDate = Timestamp.valueOf(creditCardJson.getString("expiration_date"));
                CreditCard creditCard = new CreditCard(
                        creditCardJson.getInt("credit_card_id"),
                        creditCardJson.getString("cardholder_name"),
                        creditCardJson.getString("card_number"),
                        creditCardJson.getString("issuing_bank"),
                        creditCardJson.getDouble("balance"),
                        expirationDate,
                        creditCardJson.getString("security_code")
                );

                creditCardList.add(creditCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return creditCardList;
    }
}
