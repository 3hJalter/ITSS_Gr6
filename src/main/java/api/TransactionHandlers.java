package api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.TransactionController;
import utils.ControlAPI;
import utils.General;
import utils.response.Response;

import java.io.IOException;
import java.util.UUID;

public class TransactionHandlers {

    public static class TransactionListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = TransactionController.getInstance().getTransactionList();
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class ActiveTransactionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "customerId"
            String customerIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "customerId");
            int customerId = Integer.parseInt(customerIdStr);
            Object responseObject = TransactionController.getInstance().getActiveTransactionByCustomerId(customerId);
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class CreateTransactionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);

            // Parse the query parameter "customerId" and "bikeId"
            String customerIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "customerId");
            String barcodeStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "barcode");
            String transactionTypeStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "transactionType");

            // For credit card
            String cardNumber = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardNumber");
            String cardholderName = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardholderName");
            String issueBank = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "issueBank");
            int month = Integer.parseInt(ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "month"));
            int year = Integer.parseInt(ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "year"));
            String securityCode = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "securityCode");
            //

            int customerId = Integer.parseInt(customerIdStr);
            UUID barcode = UUID.fromString(barcodeStr);

            // Call the createTransaction method from TransactionController
            Response<?> response = TransactionController.getInstance().createTransaction(customerId, barcode, transactionTypeStr
                    ,cardNumber, cardholderName, issueBank, month, year, securityCode);

            // Convert the response to JSON and send the response
            String jsonResponse = General.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    public static class GetDepositHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String bikeIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "bikeId");
            int bikeId = Integer.parseInt(bikeIdStr);
            Response<?> response = TransactionController.getInstance().getDeposit(bikeId);
            String jsonResponse = General.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    public static class PauseTransactionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String transactionIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "transactionId");
            int transactionId = Integer.parseInt(transactionIdStr);
            Response<?> response = TransactionController.getInstance().pauseTransaction(transactionId);
            String jsonResponse = General.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    public static class UnPauseTransactionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String transactionIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "transactionId");
            int transactionId = Integer.parseInt(transactionIdStr);
            Response<?> response = TransactionController.getInstance().unPauseTransaction(transactionId);
            String jsonResponse = General.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }
}