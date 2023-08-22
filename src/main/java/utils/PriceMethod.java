package utils;

import bike.BikeLayer;
import transaction.Transaction;
import bike.bikeEntity.Bike;

import java.sql.Timestamp;

/**
 * The PriceMethod class provides utility methods for calculating prices and deposits
 * related to bike rentals and transactions.
 */
public class PriceMethod {
    /**
     * Retrieves the deposit amount for a specified bike.
     *
     * @param bikeId The ID of the bike for which to calculate the deposit.
     * @return The calculated deposit amount in Long.
     */
    public static Long getDeposit(Integer bikeId) {
        Bike bike = BikeLayer.getInstance().getBikeById(bikeId);
        return Double.valueOf(
                bike.getCategory().getBikePrice() * bike.getCategory().getDepositRate())
                .longValue();
    }

    /**
     * Calculates the deposit to be returned for a given transaction.
     *
     * @param transaction The transaction for which to calculate the deposit return.
     * @return The deposit amount to be returned in Long.
     */
    public static Long returnDeposit(Transaction transaction) {
        // Return deposit logic
        return transaction.getDeposit();
    }

    /**
     * Retrieves the total time of bike rental in minutes for a given transaction.
     *
     * @param transaction The transaction representing the bike rental.
     * @return The total time of rental in minutes.
     */
    public static long getTimeRentInMinutes(Transaction transaction) {
        if (transaction.getStatus().equals("active"))
            return getActiveTimeRent(transaction);
        else return transaction.getMinuteUsed();
    }

    /**
     * Calculates the active time of bike rental in minutes for a given transaction.
     *
     * @param transaction The transaction representing the bike rental.
     * @return The active time of rental in minutes.
     */
    public static long getActiveTimeRent(Transaction transaction) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        return (currentTime.getTime() - transaction.getLastPause().getTime())/60000
                + transaction.getMinuteUsed();
    }

    /**
     * Calculates the total price for a given transaction, considering rental duration.
     *
     * @param transaction The transaction representing the bike rental.
     * @return The calculated total price in Long.
     */
    public static Long getTotalPrice(Transaction transaction) {
        long timeRentInMinutes = getTimeRentInMinutes(transaction);
        long totalPrice = 0L;
        if (timeRentInMinutes <= 10) return totalPrice;
        Bike bike = BikeLayer.getInstance().getBikeById(transaction.getBike().getBikeId());
        if (timeRentInMinutes <= 30) {
            totalPrice = (long) (10000L * bike.getCategory().getPriceMultiple());
            return totalPrice;
        }
        double rate = Math.ceil((float) (timeRentInMinutes - 30) / 15);
        totalPrice = (long) ((10000 + rate * bike.getCategory().getRentPrice())
                * bike.getCategory().getPriceMultiple());
        return totalPrice;
    }

    /**
     * Calculates the total price for a given transaction over a 24-hour period,
     * including late fees and early return refunds.
     *
     * @param transaction The transaction representing the bike rental.
     * @return The calculated total price for a 24-hour period in Long.
     */
    public static long get24hTotalPrice(Transaction transaction) {
        long timeRentInMinutes = getTimeRentInMinutes(transaction);
        long totalPrice;
        if (timeRentInMinutes <= 720) { // 720 minutes = 12 hours
            // Calculate refund for returning the bike early
            long refundAmount = Math.max((12 - timeRentInMinutes / 60) * 10000L, 0);
            totalPrice = 200000L - refundAmount;
        } else if (timeRentInMinutes >= 1440) {
            totalPrice = 200000L + calculateLateFees(timeRentInMinutes);
        } else totalPrice = 200000L;
        return totalPrice;
    }
    private static long calculateLateFees(long timeRentInMinutes) {
        long lateMinutes = Math.max(timeRentInMinutes - 1440, 0); // 1440 minutes = 24 hours
        return (lateMinutes / 15) * 2000L;
    }
}
