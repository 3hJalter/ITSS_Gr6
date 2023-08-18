package bike.bikeEntity;

import category.Category;
import dock.Dock;

import java.util.UUID;

/**
 * Represents an electric bike entity that extends the basic Bike class.
 */
public class EBike extends Bike {
    private Integer battery;

    /**
     * Constructs a new EBike instance with the provided attributes.
     *
     * @param bikeId   The unique identifier for the e-bike.
     * @param bikeName The name of the e-bike.
     * @param category The category the e-bike belongs to.
     * @param dock     The dock where the e-bike is located.
     * @param image    The image URL associated with the e-bike.
     * @param barcode  The barcode associated with the e-bike.
     * @param battery  The battery capacity of the e-bike.
     */
    public EBike(Integer bikeId, String bikeName, Category category, Dock dock, String image, UUID barcode, Integer battery) {
        super(bikeId, bikeName, category, dock, image, barcode);
        this.battery = battery;
    }

    /**
     * Retrieves the battery capacity of the e-bike.
     *
     * @return The e-bike's battery capacity.
     */
    public Integer getBattery() {
        return battery;
    }

    /**
     * Sets the battery capacity of the e-bike.
     *
     * @param battery The new battery capacity to set.
     */
    public void setBattery(Integer battery) {
        this.battery = battery;
    }
}
