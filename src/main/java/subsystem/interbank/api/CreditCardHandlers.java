package subsystem.interbank.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import subsystem.interbank.controller.CreditCardController;
import utils.ControlAPI;
import utils.General;
import utils.response.Response;

import java.io.IOException;

public class CreditCardHandlers {

    public static class PayViaCardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);

            // Parse request parameters
            String cardNumber = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardNumber");
            String cardholderName = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardholderName");
            String issueBank = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "issueBank");
            int month = Integer.parseInt(ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "month"));
            int year = Integer.parseInt(ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "year"));
            String securityCode = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "securityCode");
            Double amount = Double.parseDouble(ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "amount"));

            // Call CreditCardController's payViaCard method
            Response<Integer> response = CreditCardController.getInstance().payViaCard(cardNumber, cardholderName, issueBank, month, year, securityCode, amount);

            // Convert response to JSON and send the response
            String jsonResponse = General.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    public static class ReceiveMoneyViaCardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);

            // Parse request parameters
            String cardNumber = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardNumber");
            Double amount = Double.parseDouble(ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "amount"));

            // Call CreditCardController's receiveMoneyViaCard method
            Response<Integer> response = CreditCardController.getInstance().receiveMoneyViaCard(cardNumber, amount);

            // Convert response to JSON and send the response
            String jsonResponse = General.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    public static class GetBalanceFromCardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);

            // Parse request parameters
            String cardNumber = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardNumber");

            // Call CreditCardController's getBalanceFromCard method
            Response<Double> response = CreditCardController.getInstance().getBalanceFromCard(cardNumber);

            // Convert response to JSON and send the response
            String jsonResponse = General.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    public static class ResetCardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);

            // Parse request parameters
            String cardNumber = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardNumber");

            // Call CreditCardController's resetCard method
            Response<?> response = CreditCardController.getInstance().resetCard(cardNumber);

            // Convert response to JSON and send the response
            String jsonResponse = General.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }
}
