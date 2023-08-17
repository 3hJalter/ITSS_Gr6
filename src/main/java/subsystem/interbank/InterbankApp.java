package subsystem.interbank;

import subsystem.interbank.api.InterbankAPIURL;

import java.io.IOException;

public class InterbankApp {
    public static void main(String[] args) throws IOException {
        InterbankAPIURL.startServer();
    }
}
