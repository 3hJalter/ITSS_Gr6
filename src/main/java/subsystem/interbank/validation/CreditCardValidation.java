package subsystem.interbank.validation;

import subsystem.interbank.database.CreditCardLayer;
import subsystem.interbank.entity.CreditCard;
import utils.response.ResponseMessage;

import java.sql.Timestamp;
import java.util.Calendar;

public class CreditCardValidation {
    private static boolean isExist(String cardNumber){
        for (CreditCard creditCard : CreditCardLayer.getInstance().getCreditCardList()) {
            if (creditCard.getCardNumber().equals(cardNumber)) return true;
        }
        return false;
    }

    private static boolean hasCardNumber(String cardNumber){
        return cardNumber != null;
    }

    public static ResponseMessage validate(String cardNumber){
        if (!hasCardNumber(cardNumber)) return CreditCardResponseMessage.CREDIT_CARD_ID_NUMBER_INVALID;
        if (!isExist(cardNumber)) return CreditCardResponseMessage.CREDIT_CARD_NOT_EXIST;
        return CreditCardResponseMessage.SUCCESSFUL;
    }

    public static ResponseMessage authentication(CreditCard creditCard
            , String cardholderName, String issuingBank, int month, int year, String securityCode) {
        if (!creditCard.getCardholderName().equals(cardholderName))
            return CreditCardResponseMessage.CARDHOLDER_NAME_INVALID;
        if (!creditCard.getIssuingBank().equals(issuingBank))
            return CreditCardResponseMessage.ISSUING_BANK_IS_INVALID;
        Timestamp convert = convertMonthAndYearToTimestamp(month, year);
        if (convert == null)
            return CreditCardResponseMessage.EXPIRATION_DATE_IS_INVALID;
        if (!creditCard.getExpirationDate().equals(convert))
            return CreditCardResponseMessage.WRONG_EXPIRATION_DATE;
        if (!creditCard.getSecurityCode().equals(securityCode))
            return CreditCardResponseMessage.SECURITY_CODE_IS_INVALID;
        return CreditCardResponseMessage.SUCCESSFUL;
    }

    private static Timestamp convertMonthAndYearToTimestamp(int month, int year) {
        // Check if month and year are within valid ranges
        if (month < 1 || month > 12 || year < 0 || year > 99) {
            return null;
        }

        // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();

        // Set the year and month (subtracting 1 from month to match Calendar's 0-based indexing)
        calendar.set(Calendar.YEAR, year + 2000); // Assuming year 1 corresponds to 2001
        calendar.set(Calendar.MONTH, month - 1);

        // Set day, hour, minute, and second to create a timestamp at 0 hour 0 minute 0 second
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Get the time in milliseconds and create a Timestamp object
        long timeInMillis = calendar.getTimeInMillis();
        return new Timestamp(timeInMillis);
    }
}
