package api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.TransactionController;
import utils.ControlAPI;
import utils.General;
import utils.response.Response;

import java.io.IOException;

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
            String bikeIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "bikeId");

            int customerId = Integer.parseInt(customerIdStr);
            int bikeId = Integer.parseInt(bikeIdStr);

            // Call the createTransaction method from TransactionController
            Response<?> response = TransactionController.getInstance().createTransaction(customerId, bikeId);

            // Convert the response to JSON and send the response
            String jsonResponse = General.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }
}