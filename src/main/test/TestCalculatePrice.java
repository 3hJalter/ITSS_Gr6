import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCalculatePrice {

    private static long calculateLateFees(long timeRentInMinutes) {
        long lateMinutes = Math.max(timeRentInMinutes - 1440, 0); // 1440 minutes = 24 hours
        return (lateMinutes / 15) * 2000L;
    }

    @ParameterizedTest
    @CsvSource({
            "70, 19000, 1, 3000",
    }) // Add more rows as needed
    public void TestTotalPriceMethod(long timeRentInMinutes, long expectedPrice
            , double priceMultiple, long rentPrice) {
        long totalPrice;
        if (timeRentInMinutes <= 10) {
            totalPrice = 0;
        } else if (timeRentInMinutes <= 30) {
            totalPrice = (long) (10000L * priceMultiple);
        } else {
            double rate = Math.ceil((float) (timeRentInMinutes - 30) / 15);
            totalPrice = (long) ((10000 + rate * rentPrice) * priceMultiple);
        }
        assertEquals(totalPrice, expectedPrice);
    }

    @ParameterizedTest
    @CsvSource({
            "659, 180000",
    }) // Add more rows as needed
    public void Test24hTotalPriceMethod(long timeRentInMinutes, long expectedPrice) {
        long totalPrice;
        if (timeRentInMinutes <= 720) { // 720 minutes = 12 hours
            // Calculate refund for returning the bike early
            long refundAmount = Math.max((12 - timeRentInMinutes / 60) * 10000L, 0);
            totalPrice = 200000L - refundAmount;
        } else if (timeRentInMinutes >= 1440) {
            totalPrice = 200000L + calculateLateFees(timeRentInMinutes);
        } else totalPrice = 200000L;
        assertEquals(totalPrice, expectedPrice);
    }
}
