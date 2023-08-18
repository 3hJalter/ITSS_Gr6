package subsystem.interbank.api;

import com.sun.net.httpserver.HttpServer;
import subsystem.interbank.creditCard.CreditCardHandlers;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * The InterbankAPIURL class represents a utility for starting an HTTP server that exposes
 * endpoints related to credit card operations within the interbank subsystem.
 */
public class InterbankAPIURL {

    /**
     * Starts an HTTP server to handle credit card operations.
     * <p>
     * This method creates an instance of an HTTP server and configures it to listen on
     * a specific port. It then associates various endpoint contexts with corresponding
     * handler instances from the {@link subsystem.interbank.creditCard.CreditCardHandlers}
     * package to handle different credit card operations.
     * <p>
     * The following endpoints are configured:
     * - "/creditCard/pay" for processing payments via credit card.
     * - "/creditCard/receive" for receiving money via credit card.
     * - "/creditCard/getBalance" for retrieving the balance from a credit card.
     * - "/creditCard/reset" for resetting credit card information.
     * <p>
     * Once the server is properly configured, it is started and begins listening for
     * incoming HTTP requests.
     *
     * @throws IOException if an I/O error occurs while starting the server.
     */
    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
        server.createContext("/creditCard/pay", new CreditCardHandlers.PayViaCardHandler());
        server.createContext("/creditCard/receive", new CreditCardHandlers.ReceiveMoneyViaCardHandler());
        server.createContext("/creditCard/getBalance", new CreditCardHandlers.GetBalanceFromCardHandler());
        server.createContext("/creditCard/reset", new CreditCardHandlers.ResetCardHandler());
        server.start();
    }
}
