package api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.InvoiceController;
import utils.ControlAPI;
import utils.General;
import utils.response.Response;

import java.io.IOException;

public class InvoiceHandlers {

    public static class InvoiceListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = InvoiceController.getInstance().getInvoiceList();
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class InvoiceByIdHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "id"
            String idStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "id");
            int id = Integer.parseInt(idStr);
            Object responseObject = InvoiceController.getInstance().getInvoiceById(id);
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class InvoiceByCustomerIdHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "customerId"
            String customerIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "customerId");
            int customerId = Integer.parseInt(customerIdStr);
            Object responseObject = InvoiceController.getInstance().getInvoiceByCustomerId(customerId);
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class CreateInvoiceHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);

            // Parse the query parameter "transactionId" and "dockId"
            String transactionIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "transactionId");
            String dockIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "dockId");

            // For credit card
            String cardNumber = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardNumber");
            String cardholderName = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "cardholderName");
            String issueBank = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "issueBank");
            int month = Integer.parseInt(ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "month"));
            int year = Integer.parseInt(ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "year"));
            String securityCode = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "securityCode");
            //

            int transactionId = Integer.parseInt(transactionIdStr);
            int dockId = Integer.parseInt(dockIdStr);

            // Call the createInvoice method from InvoiceController
            Response<?> response = InvoiceController.getInstance().createInvoice(transactionId, dockId
                    ,cardNumber, cardholderName, issueBank, month, year, securityCode);

            // Convert the response to JSON and send the response
            String jsonResponse = General.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }
}
