package api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.BikeController;
import utils.ControlAPI;
import utils.General;

import java.io.IOException;

public class BikeHandlers {

    public static class BikeListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = BikeController.getInstance().getBikeList();
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class EBikeListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = BikeController.getInstance().getEBikeList();
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class BikeByDockIdHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "dockId"
            String dockIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "dockId");
            int dockId = Integer.parseInt(dockIdStr);
            Object responseObject = BikeController.getInstance().getBikeByDockId(dockId);
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class BikeByCategoryIdHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "categoryId"
            String categoryIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "categoryId");
            int categoryId = Integer.parseInt(categoryIdStr);
            Object responseObject = BikeController.getInstance().getBikeByCategoryId(categoryId);
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class BikeInfoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "id"
            String idStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "id");
            int id = Integer.parseInt(idStr);
            Object responseObject = BikeController.getInstance().getBikeById(id);
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }
}