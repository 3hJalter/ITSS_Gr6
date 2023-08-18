package category;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import utils.JsonFunction;
import utils.api.ControlAPI;

import java.io.IOException;

/**
 * The CategoryHandlers class provides HTTP request handlers for managing category-related API endpoints.
 */
public class CategoryHandlers {

    /**
     * Handles HTTP requests for retrieving a list of categories.
     */
    public static class CategoryListHandler implements HttpHandler {
        /**
         * Handles an HTTP request to retrieve a list of categories.
         *
         * @param exchange The HttpExchange object representing the incoming HTTP request and response.
         * @throws IOException If an I/O error occurs while processing the request or response.
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Set CORS headers to allow cross-origin requests
            ControlAPI.setCorsHeaders(exchange);

            // Retrieve the list of categories from the CategoryController
            Object responseObject = CategoryController.getInstance().getCategoryList();

            // Convert the response object to JSON format
            String response = JsonFunction.convertToJson(responseObject);

            // Send the JSON response back to the client
            ControlAPI.sendResponse(exchange, response);
        }
    }
}
