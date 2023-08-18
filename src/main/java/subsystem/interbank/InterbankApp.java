package subsystem.interbank;

import subsystem.interbank.api.InterbankAPIURL;

import java.io.IOException;

/**
 * The InterbankApp class serves as the entry point for the Interbank subsystem application.
 * It starts the Interbank API server to handle credit card-related transactions.
 */
public class InterbankApp {
    /**
     * The main method that starts the Interbank API server.
     *
     * @param args Command line arguments (not used).
     * @throws IOException If an I/O error occurs while starting the server.
     */
    public static void main(String[] args) throws IOException {
        InterbankAPIURL.startServer();
    }
}
