package utils;

import bike.BikeLayer;
import transaction.Transaction;
import bike.bikeEntity.Bike;

import java.sql.Timestamp;

public class PriceMethod {
    public static Long getDeposit(Integer bikeId) {
        Bike bike = BikeLayer.getInstance().getBikeById(bikeId);
        return Double.valueOf(
                bike.getCategory().getBikePrice() * bike.getCategory().getDepositRate())
                .longValue();
    }

    public static Long returnDeposit(Transaction transaction) {
        // Return deposit logic
        return transaction.getDeposit();
    }

    public static long getTimeRentInMinutes(Transaction transaction) {
        if (transaction.getStatus().equals("active"))
            return getActiveTimeRent(transaction);
        else return transaction.getMinuteUsed();
    }

    public static long getActiveTimeRent(Transaction transaction) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        return (currentTime.getTime() - transaction.getLastPause().getTime())/60000
                + transaction.getMinuteUsed();
    }

    public static Long getTotalPrice(Transaction transaction) {
        long timeRentInMinutes = getTimeRentInMinutes(transaction);
        long totalPrice = 0L;
        if (timeRentInMinutes <= 10) return totalPrice;
        Bike bike = BikeLayer.getInstance().getBikeById(transaction.getBike().getBikeId());
        if (timeRentInMinutes <= 30) {
            totalPrice = (long) (10000L * bike.getCategory().getPriceMultiple());
            return totalPrice;
        }
        totalPrice = (long) ((10000 + (timeRentInMinutes - 30) * bike.getCategory().getRentPrice())
                * bike.getCategory().getPriceMultiple());
        return totalPrice;
    }

    public static long get24hTotalPrice(Transaction transaction) {
        long timeRentInMinutes = getTimeRentInMinutes(transaction);
        long totalPrice;
        if (timeRentInMinutes <= 720) { // 720 minutes = 12 hours
            // Calculate refund for returning the bike early
            long refundAmount = Math.max((720 - timeRentInMinutes) * 10000L / 60, 0);
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
