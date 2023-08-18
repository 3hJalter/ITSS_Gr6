package invoice;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import utils.JsonFunction;
import utils.SyntaxChecker;
import utils.api.ControlAPI;
import utils.response.Response;

import java.io.IOException;

/**
 * The InvoiceHandlers class contains HTTP request handlers for invoice-related endpoints.
 * These handlers interact with the InvoiceController to process requests and provide responses.
 */
public class InvoiceHandlers {

    /**
     * HTTP handler for retrieving a list of all invoices.
     */
    public static class InvoiceListHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve a list of all invoices.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = InvoiceController.getInstance().getInvoiceList();
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    /**
     * HTTP handler for retrieving information about an invoice by its ID.
     */
    public static class InvoiceByIdHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve information about an invoice by its ID.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String idStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "id");
            int id = Integer.parseInt(idStr);
            Object responseObject = InvoiceController.getInstance().getInvoiceById(id);
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    /**
     * HTTP handler for retrieving invoices associated with a specific customer.
     */
    public static class InvoiceByCustomerIdHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve invoices associated with a specific customer.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String customerIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "customerId");
            int customerId = Integer.parseInt(customerIdStr);
            Object responseObject = InvoiceController.getInstance().getInvoiceByCustomerId(customerId);
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    /**
     * HTTP handler for creating a new invoice.
     */
    public static class CreateInvoiceHandler implements HttpHandler {
        /**
         * Handles an HTTP request to create a new invoice.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String transactionIdStr = SyntaxChecker.parseAndCheckIntegerParameter(exchange, "transactionId");
            String dockIdStr = SyntaxChecker.parseAndCheckIntegerParameter(exchange, "dockId");
            // For credit card
            String cardNumber = SyntaxChecker.parseAndCheckCardNumberParameter(exchange, "cardNumber");
            String cardholderName = SyntaxChecker.parseAndCheckCardHolderNameParameter(exchange, "cardholderName");
            String issueBank = SyntaxChecker.parseAndCheckIssuingBankParameter(exchange, "issueBank");
            String monthStr = SyntaxChecker.parseAndCheckMonthParameter(exchange, "month");
            String yearStr = SyntaxChecker.parseAndCheckYearParameter(exchange, "year");
            String securityCode = SyntaxChecker.parseAndCheckSecurityCodeParameter(exchange, "securityCode");
            //

            int month = Integer.parseInt(monthStr);
            int year = Integer.parseInt(yearStr);
            int transactionId = Integer.parseInt(transactionIdStr);
            int dockId = Integer.parseInt(dockIdStr);

            // Call the createInvoice method from InvoiceController
            Response<?> response = InvoiceController.getInstance().createInvoice(transactionId, dockId,
                    cardNumber, cardholderName, issueBank, month, year, securityCode);

            // Convert the response to JSON and send the response
            String jsonResponse = JsonFunction.convertToJson(response);
            ControlAPI.sendResponse(exchange, jsonResponse);
        }
    }
}
