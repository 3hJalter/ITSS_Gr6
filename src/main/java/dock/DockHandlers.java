package dock;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import utils.JsonFunction;
import utils.SyntaxChecker;
import utils.api.ControlAPI;

import java.io.IOException;

public class DockHandlers {

    public static class DockListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = DockController.getInstance().getDockList();
            String response = JsonFunction.convertToJson(responseObject);
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
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    public static class DockInfoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            // Parse the query parameter "id"
            String idStr = SyntaxChecker.parseAndCheckIntegerParameter(exchange, "id");
            int id = Integer.parseInt(idStr);
            Object responseObject = DockController.getInstance().getDockById(id);
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }
}