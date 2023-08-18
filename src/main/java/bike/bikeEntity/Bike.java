package bike.bikeEntity;

import category.Category;
import dock.Dock;

import java.util.UUID;

/**
 * Represents a bike entity with various attributes.
 */
public class Bike {
    private Integer bikeId;
    private String bikeName;
    private Category category;
    private Dock dock;
    private String image;
    private UUID barcode;

    /**
     * Constructs a new Bike instance with the provided attributes.
     *
     * @param bikeId   The unique identifier for the bike.
     * @param bikeName The name of the bike.
     * @param category The category the bike belongs to.
     * @param dock     The dock where the bike is located.
     * @param image    The image URL associated with the bike.
     * @param barcode  The barcode associated with the bike.
     */
    public Bike(Integer bikeId, String bikeName, Category category, Dock dock, String image, UUID barcode) {
        this.bikeId = bikeId;
        this.bikeName = bikeName;
        this.category = category;
        this.dock = dock;
        this.image = image;
        this.barcode = barcode;
    }

    /**
     * Retrieves the unique identifier of the bike.
     *
     * @return The bike's unique identifier.
     */
    public Integer getBikeId() {
        return bikeId;
    }

    /**
     * Sets the unique identifier of the bike.
     *
     * @param bikeId The new unique identifier to set.
     */
    public void setBikeId(Integer bikeId) {
        this.bikeId = bikeId;
    }

    /**
     * Retrieves the name of the bike.
     *
     * @return The bike's name.
     */
    public String getBikeName() {
        return bikeName;
    }

    /**
     * Sets the name of the bike.
     *
     * @param bikeName The new name to set.
     */
    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    /**
     * Retrieves the category of the bike.
     *
     * @return The bike's category.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category of the bike.
     *
     * @param category The new category to set.
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Retrieves the dock where the bike is located.
     *
     * @return The bike's dock.
     */
    public Dock getDock() {
        return dock;
    }

    /**
     * Sets the dock where the bike is located.
     *
     * @param dock The new dock to set.
     */
    public void setDock(Dock dock) {
        this.dock = dock;
    }

    /**
     * Retrieves the image URL associated with the bike.
     *
     * @return The bike's image URL.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image URL associated with the bike.
     *
     * @param image The new image URL to set.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Retrieves the barcode associated with the bike.
     *
     * @return The bike's barcode.
     */
    public UUID getBarcode() {
        return barcode;
    }

    /**
     * Sets the barcode associated with the bike.
     *
     * @param barcode The new barcode to set.
     */
    public void setBarcode(UUID barcode) {
        this.barcode = barcode;
    }
}
