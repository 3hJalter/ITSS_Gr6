package utils.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class APIInterbankHandlers {
    public static HttpClient httpClient = HttpClients.createDefault();
    static String baseReceiveURL = "http://localhost:8001/creditCard/receive";
    static String basePayURL = "http://localhost:8001/creditCard/pay";

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

    public static String payWithCard(String cardNumber, String cardholderName, String issueBank,
                                     int month, int year, String securityCode, double amount) {
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

    public static String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8)
                .replace("+", "%20");
    }

}
