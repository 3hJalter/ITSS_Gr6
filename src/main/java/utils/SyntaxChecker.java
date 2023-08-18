package utils;

import com.sun.net.httpserver.HttpExchange;
import utils.api.ControlAPI;
import utils.response.Response;
import utils.response.responseMessageImpl.SyntaxResponseMessage;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxChecker {
    private static boolean isInteger(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static String parseAndCheckIntegerParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        boolean isInteger = SyntaxChecker.isInteger(parameterValue);
        if (!isInteger) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(SyntaxResponseMessage.INVALID_ID_INPUT)));
            throw new IllegalArgumentException("Invalid parameter: " + parameterName);
        }
        return parameterValue;
    }

    private static boolean isMonth(Integer month) {
        return month >= 1 && month <= 12;
    }

    public static String parseAndCheckMonthParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        boolean isInteger = SyntaxChecker.isInteger(parameterValue);
        if (!isInteger) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(SyntaxResponseMessage.INVALID_MONTH_INPUT)));
            throw new IllegalArgumentException("Invalid parameter: " + parameterName);
        }
        boolean isMonth = SyntaxChecker.isMonth(Integer.parseInt(parameterValue));
        if (!isMonth) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(SyntaxResponseMessage.INVALID_MONTH_INPUT)));
            throw new IllegalArgumentException("Invalid parameter: " + parameterName);
        }
        return parameterValue;
    }

    private static boolean isYear(Integer year) {
        return year >= 0 && year <= 99;
    }

    public static String parseAndCheckYearParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        boolean isInteger = SyntaxChecker.isInteger(parameterValue);
        if (!isInteger) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(SyntaxResponseMessage.INVALID_YEAR_INPUT)));
            throw new IllegalArgumentException("Invalid parameter: " + parameterName);
        }
        boolean isYear = SyntaxChecker.isYear(Integer.parseInt(parameterValue));
        if (!isYear) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(SyntaxResponseMessage.INVALID_YEAR_INPUT)));
            throw new IllegalArgumentException("Invalid parameter: " + parameterName);
        }
        return parameterValue;
    }

    private static boolean isUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    public static String parseAndCheckUUIDParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        boolean isUUID = SyntaxChecker.isUUID(parameterValue);
        if (!isUUID) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(SyntaxResponseMessage.INVALID_BARCODE_UUID)));
            throw new IllegalArgumentException("Invalid parameter: " + parameterName);
        }
        return parameterValue;
    }

    private static boolean isCardNumber(String number) {
        Pattern pattern = Pattern.compile("^\\d{16}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static String parseAndCheckCardNumberParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        boolean isValidCardNumber = isCardNumber(parameterValue);
        if (!isValidCardNumber) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(SyntaxResponseMessage.INVALID_CARD_NUMBER)));
            throw new IllegalArgumentException("Invalid card number parameter: " + parameterName);
        }
        return parameterValue;
    }

    private static boolean isValidName(String name) {
        // Check if the name starts with a non-space character
        if (name == null || name.isEmpty() || Character.isWhitespace(name.charAt(0))) {
            return false;
        }
        // Check if the name contains only letters and spaces
        Pattern pattern = Pattern.compile("^[a-zA-Z\\s]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static String parseAndCheckCardHolderNameParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        boolean isValidCardHolderName = isValidName(parameterValue);
        if (!isValidCardHolderName) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(SyntaxResponseMessage.INVALID_CARDHOLDER_NAME)));
            throw new IllegalArgumentException("Invalid cardholder name parameter: " + parameterName);
        }
        return parameterValue;
    }

    public static String parseAndCheckIssuingBankParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        boolean isValidCardHolderName = isValidName(parameterValue);
        if (!isValidCardHolderName) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(SyntaxResponseMessage.INVALID_ISSUING_BANK)));
            throw new IllegalArgumentException("Invalid cardholder name parameter: " + parameterName);
        }
        return parameterValue;
    }

    private static boolean isValidSecurityCode(String code) {
        Pattern pattern = Pattern.compile("^\\d{3}$");
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

    public static String parseAndCheckSecurityCodeParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        boolean isValidSecurityCode = isValidSecurityCode(parameterValue);
        if (!isValidSecurityCode) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(SyntaxResponseMessage.INVALID_SECURITY_CODE)));
            throw new IllegalArgumentException("Invalid security code parameter: " + parameterName);
        }
        return parameterValue;
    }

    private static boolean isAmount(String id) {
        try {
            Double.parseDouble(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String parseAndCheckDoubleParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        boolean isInteger = SyntaxChecker.isInteger(parameterValue);
        if (!isInteger) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(SyntaxResponseMessage.INVALID_AMOUNT_INPUT)));
            throw new IllegalArgumentException("Invalid parameter: " + parameterName);
        }
        return parameterValue;
    }
}
