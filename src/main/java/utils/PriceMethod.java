package utils;

import database.entityLayer.BikeLayer;
import entity.Transaction;
import entity.bike.Bike;

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
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        return (currentTime.getTime() - transaction.getCreatedAt().getTime())/60000;
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
}
