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

public class CreditCardLayer {
    private static IDatabaseConnection databaseConnection;
    private static CreditCardLayer instance;
    JSONArray jsonArray;

    private CreditCardLayer() {
        if (databaseConnection == null)
            databaseConnection = InterbankPostgresConnection.getInstance();
        setJsonArray();
    }

    public static CreditCardLayer getInstance() {
        if (instance == null) {
            instance = new CreditCardLayer();
        }
        return instance;
    }

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

    public List<CreditCard> getCreditCardList() {
        return getCreditCardFromJSON();
    }

    public CreditCard getCreditCardByCardNumber(String cardNumber) {
        if (cardNumber == null) return null;
        for (CreditCard CreditCard : getCreditCardFromJSON()) {
            if (CreditCard.getCardNumber().equals(cardNumber)) return CreditCard;
        }
        return null;
    }

    public CreditCard getCreditCardByCardId(Integer cardId) {
        if (cardId == null) return null;
        for (CreditCard CreditCard : getCreditCardFromJSON()) {
            if (CreditCard.getCreditCardId().equals(cardId)) return CreditCard;
        }
        return null;
    }

    public Double getBalance(String cardNumber) {
        if (cardNumber == null) return -1D;
        CreditCard creditCard = getCreditCardByCardNumber(cardNumber);
        if (creditCard == null) return -1D;
        return creditCard.getBalance();
    }

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

    private List<CreditCard> getCreditCardFromJSON() {
        List<CreditCard> CreditCardList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject CreditCardJson = jsonArray.getJSONObject(i);
                Timestamp expirationDate = Timestamp.valueOf(CreditCardJson.getString("expiration_date"));
                CreditCard CreditCard = new CreditCard(CreditCardJson.getInt("credit_card_id"),
                        CreditCardJson.getString("cardholder_name"),
                        CreditCardJson.getString("card_number"),
                        CreditCardJson.getString("issuing_bank"),
                        CreditCardJson.getDouble("balance"),
                        expirationDate,
                        CreditCardJson.getString("security_code"));

                CreditCardList.add(CreditCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CreditCardList;
    }
}
