package subsystem.interbank.creditCard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import utils.JsonFunction;
import utils.api.ControlAPI;
import utils.response.Response;

import java.io.IOException;

/**
 * The CreditCardHandlers class contains HTTP request handlers for credit card-related endpoints.
 * These handlers interact with the CreditCardController to process requests and provide responses.
 */
public class CreditCardHandlers {

    /**
     * HTTP handler for making a payment via a credit card.
     */
    public static class PayViaCardHandler implements HttpHandler {
        /**
         * Handles an HTTP request to make a payment via a credit card.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
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
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    /**
     * HTTP handler for receiving money via a credit card.
     */
    public static class ReceiveMoneyViaCardHandler implements HttpHandler {
        /**
         * Handles an HTTP request to receive money via a credit card.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);

            // Parse request parameters
            String cardNumber = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardNumber");
            String amountStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "amount");

            Double amount = Double.parseDouble(amountStr);
            // Call CreditCardController's receiveMoneyViaCard method
            Response<Integer> response = CreditCardController.getInstance().receiveMoneyViaCard(cardNumber, amount);

            // Convert response to JSON and send the response
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    /**
     * HTTP handler for getting the balance from a credit card.
     */
    public static class GetBalanceFromCardHandler implements HttpHandler {
        /**
         * Handles an HTTP request to get the balance from a credit card.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);

            // Parse request parameters
            String cardNumber = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardNumber");

            // Call CreditCardController's getBalanceFromCard method
            Response<Double> response = CreditCardController.getInstance().getBalanceFromCard(cardNumber);

            // Convert response to JSON and send the response
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    /**
     * HTTP handler for resetting a credit card.
     */
    public static class ResetCardHandler implements HttpHandler {
        /**
         * Handles an HTTP request to reset a credit card.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);

            // Parse request parameters
            String cardNumber = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardNumber");

            // Call CreditCardController's resetCard method
            Response<?> response = CreditCardController.getInstance().resetCard(cardNumber);

            // Convert response to JSON and send the response
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }
}
