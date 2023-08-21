package utils.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import utils.SyntaxChecker;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.SyntaxResponseMessage;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * The APIInterbankHandlers class provides utility methods for interacting with the Interbank API
 * to perform credit card transactions such as receiving money and making payments.
 */
public class APIInterbankHandlers {
    public static HttpClient httpClient = HttpClients.createDefault();
    static String baseReceiveURL = "http://localhost:8001/creditCard/receive";
    static String basePayURL = "http://localhost:8001/creditCard/pay";

    /**
     * Sends a request to the Interbank API to receive money into a specified credit card.
     *
     * @param cardNumber The credit card number to receive money into.
     * @param amount     The amount of money to receive.
     */
    public static void receiveMoney(String cardNumber, double amount) {

        String receiveURL = baseReceiveURL + "?cardNumber=" + cardNumber + "&amount=" + amount;

        HttpPatch httpPatch = new HttpPatch(receiveURL);

        try {
            // Execute the request and get the response
            HttpResponse response = httpClient.execute(httpPatch);
            // Check the response status code
            int statusCode = response.getStatusLine().getStatusCode();
            // Get the response content
            String responseBody = EntityUtils.toString(response.getEntity());
            // Parse the response JSON
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            String message = jsonObject.get("message").getAsString();
            if (statusCode == 200) {
                // Process the response body as needed
                System.out.println("Receive response: " + responseBody);
            } else {
                // Handle unexpected status codes
                System.out.println("Unexpected status code: " + statusCode);
            }
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    /**
     * Sends a request to the Interbank API to make a payment using a credit card.
     *
     * @param cardNumber      The credit card number for payment.
     * @param cardholderName  The name of the cardholder.
     * @param issueBank       The issuing bank of the card.
     * @param month           The expiration month of the card.
     * @param year            The expiration year of the card.
     * @param securityCode    The security code of the card.
     * @param amount          The amount of money to pay.
     * @return A message indicating the status of the payment.
     */
    public static String payWithCard(String cardNumber, String cardholderName, String issueBank,
                                     String month, String year, String securityCode, double amount) {
        ResponseMessage responseMessage = checkCardSyntax(cardNumber, cardholderName, issueBank,
                month, year, securityCode);
        if (responseMessage != SyntaxResponseMessage.SUCCESSFUL)
            return responseMessage.getMessage();
        String encodedCardholderName = urlEncode(cardholderName);
        String encodedIssueBank = urlEncode(issueBank);
        String payURL = basePayURL + "?cardNumber=" + cardNumber +
                "&cardholderName=" + encodedCardholderName +
                "&issueBank=" + encodedIssueBank +
                "&month=" + month +
                "&year=" + year +
                "&securityCode=" + securityCode +
                "&amount=" + amount;

        HttpPatch httpPatch = new HttpPatch(payURL);

        try {
            // Execute the request and get the response
            HttpResponse response = httpClient.execute(httpPatch);

            // Check the response status code
            int statusCode = response.getStatusLine().getStatusCode();
            // Get the response content
            String responseBody = EntityUtils.toString(response.getEntity());
            // Parse the response JSON
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            String message = jsonObject.get("message").getAsString();
            if (statusCode == 200) {
                // Process the response body as needed
                System.out.println("Pay response: " + responseBody);
            } else {
                // Handle unexpected status codes
                System.out.println("Unexpected status code: " + statusCode);
            }
            return message;
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
            return "Error when trying to call Interbank API";
        }
    }

    /**
     * URL encodes the given value using UTF-8 encoding.
     *
     * @param value The value to encode.
     * @return The URL-encoded value.
     */
    private static String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8)
                .replace("+", "%20");
    }

    private static ResponseMessage checkCardSyntax(String cardNumber, String cardholderName, String issueBank,
                                                   String month, String year, String securityCode){
        ResponseMessage success = SyntaxResponseMessage.SUCCESSFUL;
        ResponseMessage response = SyntaxChecker.isCardNumber(cardNumber);
        if (response != success) return response;
        response = SyntaxChecker.isValidName(cardholderName);
        if (response != success) return SyntaxResponseMessage.INVALID_CARDHOLDER_NAME;
        response = SyntaxChecker.isValidName(issueBank);
        if (response != success) return SyntaxResponseMessage.INVALID_ISSUING_BANK;
        response = SyntaxChecker.isMonth(month);
        if (response != success) return response;
        response = SyntaxChecker.isYear(year);
        if (response != success) return response;
        response = SyntaxChecker.isValidSecurityCode(securityCode);
        return response;
    }
}
