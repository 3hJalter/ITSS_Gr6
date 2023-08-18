package transaction;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import utils.JsonFunction;
import utils.SyntaxChecker;
import utils.api.ControlAPI;
import utils.response.Response;

import java.io.IOException;
import java.util.UUID;

public class TransactionHandlers {

    public static class TransactionListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = TransactionController.getInstance().getTransactionList();
            String response = JsonFunction.convertToJson(responseObject);
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
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class CreateTransactionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);

            // Parse the query parameter "customerId" and "bikeId"
            String customerIdStr = SyntaxChecker.parseAndCheckIntegerParameter(exchange, "customerId");
            String barcodeStr = SyntaxChecker.parseAndCheckUUIDParameter(exchange, "barcode");
            String transactionTypeStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "transactionType");

            // For credit card
            String cardNumber = SyntaxChecker.parseAndCheckCardNumberParameter(exchange, "cardNumber");
            String cardholderName = SyntaxChecker.parseAndCheckCardHolderNameParameter(exchange, "cardholderName");
            String issueBank = SyntaxChecker.parseAndCheckIssuingBankParameter(exchange, "issueBank");
            String monthStr = SyntaxChecker.parseAndCheckMonthParameter(exchange, "month");
            String yearStr = SyntaxChecker.parseAndCheckYearParameter(exchange, "year");
            String securityCode = SyntaxChecker.parseAndCheckSecurityCodeParameter(exchange, "securityCode");
            //

            int customerId = Integer.parseInt(customerIdStr);
            UUID barcode = UUID.fromString(barcodeStr);
            int month = Integer.parseInt(monthStr);
            int year = Integer.parseInt(yearStr);
            // Call the createTransaction method from TransactionController
            Response<?> response = TransactionController.getInstance().createTransaction(customerId, barcode, transactionTypeStr
                    , cardNumber, cardholderName, issueBank, month, year, securityCode);

            // Convert the response to JSON and send the response
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    public static class GetDepositHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String bikeIdStr = SyntaxChecker.parseAndCheckIntegerParameter(exchange, "bikeId");
            int bikeId = Integer.parseInt(bikeIdStr);
            Response<?> response = TransactionController.getInstance().getDeposit(bikeId);
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    public static class PauseTransactionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String transactionIdStr = SyntaxChecker.parseAndCheckIntegerParameter(exchange, "transactionId");
            int transactionId = Integer.parseInt(transactionIdStr);
            Response<?> response = TransactionController.getInstance().pauseTransaction(transactionId);
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }

    public static class UnPauseTransactionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String transactionIdStr = SyntaxChecker.parseAndCheckIntegerParameter(exchange, "transactionId");
            int transactionId = Integer.parseInt(transactionIdStr);
            Response<?> response = TransactionController.getInstance().unPauseTransaction(transactionId);
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }
}