package api;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import controller.DockController;
import utils.General;

public class DockAPI {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/dock/list", new DockListHandler());
        server.createContext("/dock/search", new DockSearchHandler());
        server.createContext("/dock/bikes", new DockBikesHandler());
        server.createContext("/dock/info", new DockInfoHandler());
        server.setExecutor(null); // Use the default executor
        server.start();
    }

    static class DockListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCorsHeaders(exchange);
            Object responseObject = DockController.getInstance().getDockList();
            String response = General.convertToJson(responseObject);
            sendResponse(exchange, response);
        }
    }

    static class DockSearchHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCorsHeaders(exchange);
            // Parse the query parameter "keyword"
            String keyword = parseQueryString(exchange.getRequestURI().getQuery(), "keyword");
            Object responseObject = DockController.getInstance().searchDock(keyword);
            String response = General.convertToJson(responseObject);
            sendResponse(exchange, response);
        }
    }

    static class DockBikesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCorsHeaders(exchange);
            // Parse the query parameter "dockId"
            String dockIdStr = parseQueryString(exchange.getRequestURI().getQuery(), "dockId");
            int dockId = Integer.parseInt(dockIdStr);
            Object responseObject = DockController.getInstance().getDockById(dockId);
            String response = General.convertToJson(responseObject);
            sendResponse(exchange, response);
        }
    }

    static class DockInfoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCorsHeaders(exchange);
            // Parse the query parameter "id"
            String idStr = parseQueryString(exchange.getRequestURI().getQuery(), "id");
            int id = Integer.parseInt(idStr);
            Object responseObject = DockController.getInstance().getDockById(id);
            String response = General.convertToJson(responseObject);
            sendResponse(exchange, response);
        }
    }

    private static String parseQueryString(String queryString, String paramName) {
        if (queryString == null || paramName == null) {
            return null;
        }

        String[] params = queryString.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                if (key.equals(paramName)) {
                    return value;
                }
            }
        }
        return null;
    }

    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Content-Type", "application/json"); // Set the appropriate content type

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void setCorsHeaders(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:5173");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
    }
}