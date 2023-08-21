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

public class SyntaxChecker {
    public static ResponseMessage isId(String id) {
        try {
            Integer.parseInt(id);
            return SyntaxResponseMessage.SUCCESSFUL;
        } catch (NumberFormatException e) {
            return SyntaxResponseMessage.INVALID_ID_INPUT;
        }
    }
    public static String parseAndCheckIdParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        ResponseMessage response = SyntaxChecker.isId(parameterValue);
        CheckSyntaxResponse(exchange, parameterName, response, response);
        return parameterValue;
    }

    public static ResponseMessage isMonth(String monthStr) {
        int month;
        try {
            month = Integer.parseInt(monthStr);
        } catch (NumberFormatException e) {
            return SyntaxResponseMessage.INVALID_MONTH_INPUT;
        }
        if (month >= 1 && month <= 12) return SyntaxResponseMessage.SUCCESSFUL;
        return SyntaxResponseMessage.INVALID_MONTH_INPUT;
    }

    public static ResponseMessage isYear(String yearStr) {
        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            return SyntaxResponseMessage.INVALID_YEAR_INPUT;
        }
        if (year >= 0 && year <= 99) return SyntaxResponseMessage.SUCCESSFUL;
        return SyntaxResponseMessage.INVALID_YEAR_INPUT;
    }

    public static ResponseMessage isUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return SyntaxResponseMessage.SUCCESSFUL;
        } catch (IllegalArgumentException e) {
            return SyntaxResponseMessage.INVALID_BARCODE_UUID;
        }
    }
    public static String parseAndCheckUUIDParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        ResponseMessage response = SyntaxChecker.isUUID(parameterValue);
        CheckSyntaxResponse(exchange, parameterName, response, response);
        return parameterValue;
    }

    public static ResponseMessage isCardNumber(String number) {
        Pattern pattern = Pattern.compile("^\\d{16}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches() ? SyntaxResponseMessage.SUCCESSFUL
                : SyntaxResponseMessage.INVALID_CARD_NUMBER;
    }

    public static String parseAndCheckCardNumberParameter(HttpExchange exchange, String parameterName) throws IOException {
        String parameterValue = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), parameterName);
        ResponseMessage response = isCardNumber(parameterValue);
        CheckSyntaxResponse(exchange, parameterName, response, response);
        return parameterValue;
    }

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

    public static ResponseMessage isValidSecurityCode(String code) {
        Pattern pattern = Pattern.compile("^\\d{3}$");
        Matcher matcher = pattern.matcher(code);
        return matcher.matches() ? SyntaxResponseMessage.SUCCESSFUL
                : SyntaxResponseMessage.INVALID_SECURITY_CODE;
    }

    private static void CheckSyntaxResponse(HttpExchange exchange, String parameterName, ResponseMessage response, ResponseMessage returnResponse) throws IOException {
        if (response != SyntaxResponseMessage.SUCCESSFUL) {
            ControlAPI.sendResponse(exchange, JsonFunction.convertToJson(
                    new Response<>(returnResponse)));
            throw new IllegalArgumentException("Invalid parameter: " + parameterName);
        }
    }
}
