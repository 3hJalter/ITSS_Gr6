package subsystem.interbank.creditCard;

import utils.response.ResponseMessage;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * The CreditCardValidation class provides methods to validate and authenticate credit card information.
 */
public class CreditCardValidation {
    /**
     * Checks whether a credit card with the given card number exists in the credit card list.
     *
     * @param cardNumber The card number to check for existence.
     * @return true if the card number exists, false otherwise.
     */
    private static boolean isExist(String cardNumber) {
        for (CreditCard creditCard : CreditCardLayer.getInstance().getCreditCardList()) {
            if (creditCard.getCardNumber().equals(cardNumber)) return true;
        }
        return false;
    }

    /**
     * Checks whether a card number is not null.
     *
     * @param cardNumber The card number to check.
     * @return true if the card number is not null, false otherwise.
     */
    private static boolean hasCardNumber(String cardNumber) {
        return cardNumber != null;
    }

    /**
     * Validates a credit card based on its card number.
     *
     * @param cardNumber The card number to validate.
     * @return A ResponseMessage indicating the validation result.
     */
    public static ResponseMessage validate(String cardNumber) {
        if (!hasCardNumber(cardNumber)) return CreditCardResponseMessage.CREDIT_CARD_ID_NUMBER_INVALID;
        if (!isExist(cardNumber)) return CreditCardResponseMessage.CREDIT_CARD_NOT_EXIST;
        return CreditCardResponseMessage.SUCCESSFUL;
    }

    /**
     * Authenticates a credit card based on various parameters.
     *
     * @param creditCard       The credit card object to authenticate against.
     * @param cardholderName   The name of the cardholder.
     * @param issuingBank      The issuing bank of the credit card.
     * @param month            The expiration month of the credit card.
     * @param year             The expiration year of the credit card.
     * @param securityCode     The security code of the credit card.
     * @return A ResponseMessage indicating the authentication result.
     */
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

    /**
     * Converts a given month and year to a Timestamp object representing the expiration date.
     *
     * @param month The expiration month (1-12).
     * @param year  The last two digits of the expiration year (0-99).
     * @return A Timestamp representing the expiration date, or null if the input is invalid.
     */
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
