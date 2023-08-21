package utils;

import com.sun.net.httpserver.HttpExchange;
import utils.api.ControlAPI;
import utils.response.Response;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.SyntaxResponseMessage;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class for syntax validation and checking in various contexts.
 */
public class SyntaxChecker {

    /**
     * Checks whether the given string is a valid integer ID.
     *
     * @param id The string to be checked.
     * @return A response message indicating whether the ID is valid or not.
     */
    public static ResponseMessage isId(String id) {
        if (id == null) return SyntaxResponseMessage.INVALID_ID_INPUT;
        try {
            Integer.parseInt(id);
            return SyntaxResponseMessage.SUCCESSFUL;
        } catch (NumberFormatException e) {
            return SyntaxResponseMessage.INVALID_ID_INPUT;
        }
    }

    /**
     * Parses and validates an ID parameter from the HTTP exchange.
     *
     * @param exchange      The HTTP exchange containing the request.
     * @param parameterName The name of the parameter to be parsed and checked.
     * @return The parsed and validated ID parameter value.
     * @throws IOException If an I/O error occurs during parsing or response handling.
     */
    public static String parseAndCheckIdParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        ResponseMessage response = SyntaxChecker.isId(parameterValue);
        CheckSyntaxResponse(exchange, parameterName, response, response);
        return parameterValue;
    }

    /**
     * Checks whether the given string is a valid month.
     *
     * @param monthStr The string representing the month to be checked.
     * @return A response message indicating whether the month is valid or not.
     */
    public static ResponseMessage isMonth(String monthStr) {
        if (monthStr == null) return SyntaxResponseMessage.INVALID_MONTH_INPUT;
        int month;
        try {
            month = Integer.parseInt(monthStr);
        } catch (NumberFormatException e) {
            return SyntaxResponseMessage.INVALID_MONTH_INPUT;
        }
        if (month >= 1 && month <= 12) return SyntaxResponseMessage.SUCCESSFUL;
        return SyntaxResponseMessage.INVALID_MONTH_INPUT;
    }

    /**
     * Checks whether the given string is a valid year.
     *
     * @param yearStr The string representing the year to be checked.
     * @return A response message indicating whether the year is valid or not.
     */
    public static ResponseMessage isYear(String yearStr) {
        if (yearStr == null) return SyntaxResponseMessage.INVALID_YEAR_INPUT;
        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            return SyntaxResponseMessage.INVALID_YEAR_INPUT;
        }
        if (year >= 0 && year <= 99) return SyntaxResponseMessage.SUCCESSFUL;
        return SyntaxResponseMessage.INVALID_YEAR_INPUT;
    }

    /**
     * Checks whether the given string is a valid UUID.
     *
     * @param uuid The string to be checked as a UUID.
     * @return A response message indicating whether the UUID is valid or not.
     */
    public static ResponseMessage isUUID(String uuid) {
        if (uuid == null) return SyntaxResponseMessage.INVALID_BARCODE_UUID;
        try {
            UUID.fromString(uuid);
            return SyntaxResponseMessage.SUCCESSFUL;
        } catch (IllegalArgumentException e) {
            return SyntaxResponseMessage.INVALID_BARCODE_UUID;
        }
    }

    /**
     * Parses and validates a UUID parameter from the HTTP exchange.
     *
     * @param exchange      The HTTP exchange containing the request.
     * @param parameterName The name of the parameter to be parsed and checked.
     * @return The parsed and validated UUID parameter value.
     * @throws IOException If an I/O error occurs during parsing or response handling.
     */
    public static String parseAndCheckUUIDParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        ResponseMessage response = SyntaxChecker.isUUID(parameterValue);
        CheckSyntaxResponse(exchange, parameterName, response, response);
        return parameterValue;
    }

    /**
     * Checks whether the given string is a valid 16-digit card number.
     *
     * @param number The string representing the card number to be checked.
     * @return A response message indicating whether the card number is valid or not.
     */
    public static ResponseMessage isCardNumber(String number) {
        if (number == null) return SyntaxResponseMessage.INVALID_CARD_NUMBER;
        Pattern pattern = Pattern.compile("^\\d{16}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches() ? SyntaxResponseMessage.SUCCESSFUL
                : SyntaxResponseMessage.INVALID_CARD_NUMBER;
    }

    /**
     * Checks whether the given string is a valid name.
     *
     * @param name The string representing the name to be checked.
     * @return A response message indicating whether the name is valid or not.
     */
    public static ResponseMessage isValidName(String name) {
        // Check if the name starts with a non-space character
        if (name == null || name.isEmpty() || Character.isWhitespace(name.charAt(0))) {
            return SyntaxResponseMessage.INVALID_NAME;
        }
        // Check if the name contains only letters and spaces
        Pattern pattern = Pattern.compile("^[a-zA-Z\\s]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches() ? SyntaxResponseMessage.SUCCESSFUL
                : SyntaxResponseMessage.INVALID_NAME;
    }

    /**
     * Checks whether the given string is a valid 3-digit security code.
     *
     * @param code The string representing the security code to be checked.
     * @return A response message indicating whether the security code is valid or not.
     */
    public static ResponseMessage isValidSecurityCode(String code) {
        if (code == null) return SyntaxResponseMessage.INVALID_SECURITY_CODE;
        Pattern pattern = Pattern.compile("^\\d{3}$");
        Matcher matcher = pattern.matcher(code);
        return matcher.matches() ? SyntaxResponseMessage.SUCCESSFUL
                : SyntaxResponseMessage.INVALID_SECURITY_CODE;
    }


    /**
     * Internal method to handle syntax validation responses and send appropriate responses.
     *
     * @param exchange      The HTTP exchange for responding to the request.
     * @param parameterName The name of the parameter being checked.
     * @param response      The response message indicating syntax validation result.
     * @param returnResponse The response message to be returned when validation fails.
     * @throws IOException If an I/O error occurs during response handling.
     */
    private static void CheckSyntaxResponse(HttpExchange exchange, String parameterName, ResponseMessage response, ResponseMessage returnResponse) throws IOException {
        if (response != SyntaxResponseMessage.SUCCESSFUL) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(returnResponse)));
            throw new IllegalArgumentException("Invalid parameter: " + parameterName);
        }
    }
}
