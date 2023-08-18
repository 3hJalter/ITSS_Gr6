import utils.api.APIURL;
import java.io.IOException;

/**
 * The App class serves as the entry point for the main application.
 * It starts the HTTP server to handle API requests for various functionalities.
 */
public class App {
    /**
     * The main method that starts the HTTP server for handling API requests.
     *
     * @param args Command line arguments (not used).
     * @throws IOException If an I/O error occurs while starting the server.
     */
    public static void main(String[] args) throws IOException {
        APIURL.startServer();
    }
}
