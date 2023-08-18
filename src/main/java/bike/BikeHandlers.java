package bike;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import utils.JsonFunction;
import utils.SyntaxChecker;
import utils.api.ControlAPI;

import java.io.IOException;
import java.util.UUID;

public class BikeHandlers {

    public static class BikeListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = BikeController.getInstance().getBikeList();
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class EBikeListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = BikeController.getInstance().getEBikeList();
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class BikeByDockIdHandler implements HttpHandler {
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

    public static class BikeByCategoryIdHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "categoryId"
            String categoryIdStr = SyntaxChecker.parseAndCheckIntegerParameter(exchange, "categoryId");
            int categoryId = Integer.parseInt(categoryIdStr);
            Object responseObject = BikeController.getInstance().getBikeByCategoryId(categoryId);
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class BikeInfoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "id"
            String idStr = SyntaxChecker.parseAndCheckIntegerParameter(exchange, "id");
            int id = Integer.parseInt(idStr);
            Object responseObject = BikeController.getInstance().getBikeById(id);
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class BikeInfoByBarcodeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "barcode"
            String barcodeStr = SyntaxChecker.parseAndCheckUUIDParameter(exchange, "barcode");
            UUID barcode = UUID.fromString(barcodeStr);
            Object responseObject = BikeController.getInstance().getBikeByBarcode(barcode);
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }
}