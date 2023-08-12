package api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.DockController;
import utils.ControlAPI;
import utils.General;

import java.io.IOException;

public class DockHandlers {

    public static class DockListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = DockController.getInstance().getDockList();
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class DockSearchHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "keyword"
            String keyword = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "keyword");
            Object responseObject = DockController.getInstance().searchDock(keyword);
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class DockBikesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "dockId"
            String dockIdStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "dockId");
            int dockId = Integer.parseInt(dockIdStr);
            Object responseObject = DockController.getInstance().getDockById(dockId);
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class DockInfoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "id"
            String idStr = ControlAPI.parseQueryString(exchange.getRequestURI().getQuery(), "id");
            int id = Integer.parseInt(idStr);
            Object responseObject = DockController.getInstance().getDockById(id);
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }
}