package api;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import controller.BikeController;
import utils.General;

public class BikeAPI {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/bike/list", new BikeListHandler());
        server.createContext("/bike/ebike-list", new EBikeListHandler());
        server.createContext("/bike/dock", new BikeByDockHandler());
        server.createContext("/bike/category", new BikeByCategoryHandler());
        server.createContext("/bike/info", new BikeInfoHandler());
        server.setExecutor(null); // Use the default executor
        server.start();
    }

    static class BikeListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Object responseObject = BikeController.getInstance().getBikeList();
            String response = General.convertToJson(responseObject);
            sendResponse(exchange, response);
        }
    }

    static class EBikeListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Object responseObject = BikeController.getInstance().getEBikeList();
            String response = General.convertToJson(responseObject);
            sendResponse(exchange, response);
        }
    }

    static class BikeByDockHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String dockIdStr = parseQueryString(exchange.getRequestURI().getQuery(), "dockId");
            int dockId = Integer.parseInt(dockIdStr);
            Object responseObject = BikeController.getInstance().getBikeByDockId(dockId);
            String response = General.convertToJson(responseObject);
            sendResponse(exchange, response);
        }
    }

    static class BikeByCategoryHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String categoryIdStr = parseQueryString(exchange.getRequestURI().getQuery(), "categoryId");
            int categoryId = Integer.parseInt(categoryIdStr);
            Object responseObject = BikeController.getInstance().getBikeByCategoryId(categoryId);
            String response = General.convertToJson(responseObject);
            sendResponse(exchange, response);
        }
    }

    static class BikeInfoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String idStr = parseQueryString(exchange.getRequestURI().getQuery(), "id");
            int id = Integer.parseInt(idStr);
            Object responseObject = BikeController.getInstance().getBikeById(id);
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
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
