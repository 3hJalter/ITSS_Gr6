package bike;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import utils.JsonFunction;
import utils.SyntaxChecker;
import utils.api.ControlAPI;

import java.io.IOException;
import java.util.UUID;

/**
 * The BikeHandlers class contains HTTP request handlers for bike-related endpoints.
 * These handlers interact with the BikeController to process requests and provide responses.
 */
public class BikeHandlers {

    /**
     * HTTP handler for retrieving a list of all bikes.
     */
    public static class BikeListHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve a list of all bikes.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = BikeController.getInstance().getBikeList();
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    /**
     * HTTP handler for retrieving a list of all e-bikes.
     */
    public static class EBikeListHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve a list of all e-bikes.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = BikeController.getInstance().getEBikeList();
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    /**
     * HTTP handler for retrieving a list of bikes located at a specified dock.
     */
    public static class BikeByDockIdHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve a list of bikes located at a specified dock.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String dockIdStr = SyntaxChecker.parseAndCheckIntegerParameter(exchange, "dockId");
            int dockId = Integer.parseInt(dockIdStr);
            Object responseObject = BikeController.getInstance().getBikeByDockId(dockId);
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    /**
     * HTTP handler for retrieving a list of bikes belonging to a specified category.
     */
    public static class BikeByCategoryIdHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve a list of bikes belonging to a specified category.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String categoryIdStr = SyntaxChecker.parseAndCheckIntegerParameter(exchange, "categoryId");
            int categoryId = Integer.parseInt(categoryIdStr);
            Object responseObject = BikeController.getInstance().getBikeByCategoryId(categoryId);
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    /**
     * HTTP handler for retrieving information about a bike by its ID.
     */
    public static class BikeInfoHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve information about a bike by its ID.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String idStr = SyntaxChecker.parseAndCheckIntegerParameter(exchange, "id");
            int id = Integer.parseInt(idStr);
            Object responseObject = BikeController.getInstance().getBikeById(id);
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    /**
     * HTTP handler for retrieving information about a bike by its barcode.
     */
    public static class BikeInfoByBarcodeHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve information about a bike by its barcode.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            String barcodeStr = SyntaxChecker.parseAndCheckUUIDParameter(exchange, "barcode");
            UUID barcode = UUID.fromString(barcodeStr);
            Object responseObject = BikeController.getInstance().getBikeByBarcode(barcode);
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }
}
