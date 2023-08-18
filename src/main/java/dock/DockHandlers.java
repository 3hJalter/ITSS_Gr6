package dock;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import utils.JsonFunction;
import utils.SyntaxChecker;
import utils.api.ControlAPI;

import java.io.IOException;

/**
 * The DockHandlers class contains HTTP request handlers for dock-related endpoints.
 * These handlers interact with the DockController to process requests and provide responses.
 */
public class DockHandlers {

    /**
     * HTTP handler for retrieving a list of all docks.
     */
    public static class DockListHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve a list of all docks.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = DockController.getInstance().getDockList();
            String response = JsonFunction.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }

    /**
     * HTTP handler for searching for docks using a keyword.
     */
    public static class DockSearchHandler implements HttpHandler {
        /**
         * Handles an HTTP request to search for docks using a keyword.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
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

    /**
     * HTTP handler for retrieving information about a dock by its ID.
     */
    public static class DockInfoHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve information about a dock by its ID.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
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
