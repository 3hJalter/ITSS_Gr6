package transaction;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import utils.JsonFunction;
import utils.SyntaxChecker;
import utils.api.ControlAPI;
import utils.response.Response;

import java.io.IOException;
import java.util.UUID;

/**
 * The TransactionHandlers class contains HTTP request handlers for transaction-related endpoints.
 * These handlers interact with the TransactionController to process requests and provide responses.
 */
public class TransactionHandlers {

    /**
     * HTTP handler for retrieving a list of all transactions.
     */
    public static class TransactionListHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve a list of all transactions.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = TransactionController.getInstance().getTransactionList();
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    /**
     * HTTP handler for retrieving active transactions for a specific customer.
     */
    public static class ActiveTransactionHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve active transactions for a specific customer.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String customerIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "customerId");
            int customerId = Integer.parseInt(customerIdStr);
            Object responseObject = TransactionController.getInstance().getActiveTransactionByCustomerId(customerId);
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    /**
     * HTTP handler for creating a new transaction.
     */
    public static class CreateTransactionHandler implements HttpHandler {
        /**
         * Handles an HTTP request to create a new transaction.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Transaction Info and Checking Syntax
            String customerIdStr = SyntaxChecker.parseAndCheckIdParameter(exchange, "customerId");
            String barcodeStr = SyntaxChecker.parseAndCheckUUIDParameter(exchange, "barcode");
            String transactionTypeStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "transactionType");
            int customerId = Integer.parseInt(customerIdStr);
            UUID barcode = UUID.fromString(barcodeStr);
            // Credit Card Info
            String cardNumber = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardNumber");
            String cardholderName = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardholderName");
            String issueBank = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "issueBank");
            String monthStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "month");
            String yearStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "year");
            String securityCode = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "securityCode");

            // Call the createTransaction method from TransactionController
            Response<?> response = TransactionController.getInstance().createTransaction(customerId, barcode, transactionTypeStr,
                    cardNumber, cardholderName, issueBank, monthStr, yearStr, securityCode);

            // Convert the response to JSON and send the response
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    /**
     * HTTP handler for retrieving the deposit information for a bike.
     */
    public static class GetDepositHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve the deposit information for a bike.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String bikeIdStr = SyntaxChecker.parseAndCheckIdParameter(exchange, "bikeId");
            int bikeId = Integer.parseInt(bikeIdStr);
            Response<?> response = TransactionController.getInstance().getDeposit(bikeId);
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    /**
     * HTTP handler for pausing a transaction.
     */
    public static class PauseTransactionHandler implements HttpHandler {
        /**
         * Handles an HTTP request to pause a transaction.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String transactionIdStr = SyntaxChecker.parseAndCheckIdParameter(exchange, "transactionId");
            int transactionId = Integer.parseInt(transactionIdStr);
            Response<?> response = TransactionController.getInstance().pauseTransaction(transactionId);
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    /**
     * HTTP handler for un-pausing a transaction.
     */
    public static class UnPauseTransactionHandler implements HttpHandler {
        /**
         * Handles an HTTP request to unpause a transaction.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String transactionIdStr = SyntaxChecker.parseAndCheckIdParameter(exchange, "transactionId");
            int transactionId = Integer.parseInt(transactionIdStr);
            Response<?> response = TransactionController.getInstance().unPauseTransaction(transactionId);
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }
}
