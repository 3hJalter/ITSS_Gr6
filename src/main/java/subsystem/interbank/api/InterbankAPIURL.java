package subsystem.interbank.api;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class InterbankAPIURL {
    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
        server.createContext("/creditCard/pay", new CreditCardHandlers.PayViaCardHandler());
        server.createContext("/creditCard/receive", new CreditCardHandlers.ReceiveMoneyViaCardHandler());
        server.createContext("/creditCard/getBalance", new CreditCardHandlers.GetBalanceFromCardHandler());
        server.createContext("/creditCard/reset", new CreditCardHandlers.ResetCardHandler());
        server.start();
    }

}
