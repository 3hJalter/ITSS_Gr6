package utils.api;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

import static utils.Config.CLIENT_URL;

/**
 * The ControlAPI class provides utility methods for handling HTTP requests and responses
 * in the control API context.
 */
public class ControlAPI {

    /**
     * Parses a query string and retrieves the value of a specified parameter.
     *
     * @param queryString The query string to parse.
     * @param paramName   The name of the parameter whose value to retrieve.
     * @return The value of the specified parameter, or null if not found.
     */
    public static String parseQueryString(String queryString, String paramName) {
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

    /**
     * Sets Cross-Origin Resource Sharing (CORS) headers for the HTTP exchange.
     *
     * @param exchange The HTTP exchange to set CORS headers for.
     */
    public static void setCorsHeaders(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", CLIENT_URL);
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
    }

    /**
     * Sends an HTTP response with the provided response body.
     *
     * @param exchange The HTTP exchange to send the response for.
     * @param response The response body to send.
     * @throws IOException If an I/O error occurs while sending the response.
     */
    public static void sendResponse(HttpExchange exchange, String response) throws IOException {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Content-Type", "application/json"); // Set the appropriate content type

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
