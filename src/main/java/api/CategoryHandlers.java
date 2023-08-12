package api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.CategoryController;
import utils.ControlAPI;
import utils.General;

import java.io.IOException;

public class CategoryHandlers {

    public static class CategoryListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ControlAPI.setCorsHeaders(exchange);
            Object responseObject = CategoryController.getInstance().getCategoryList();
            String response = General.convertToJson(responseObject);
            ControlAPI.sendResponse(exchange, response);
        }
    }
}