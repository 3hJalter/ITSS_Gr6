package dock;

/**
 * The Dock class represents a docking station with a unique dock ID, a name, an image, and an address.
 * It provides methods to access and modify dock information.
 */
public class Dock {
    private Integer dockId;
    private String dockName;
    private String image;
    private String address;

    /**
     * Constructs a new Dock instance with the given dock ID, dock name, image, and address.
     *
     * @param dockId The unique identifier for the dock.
     * @param dockName The name of the dock.
     * @param image The image associated with the dock.
     * @param address The address of the dock.
     */
    public Dock(Integer dockId, String dockName, String image, String address) {
        this.dockId = dockId;
        this.dockName = dockName;
        this.image = image;
        this.address = address;
    }

    /**
     * Retrieves the dock's unique ID.
     *
     * @return The dock's unique ID.
     */
    public Integer getDockId() {
        return dockId;
    }

    /**
     * Sets the dock's unique ID.
     *
     * @param dockId The new unique ID to set for the dock.
     */
    public void setDockId(Integer dockId) {
        this.dockId = dockId;
    }

    /**
     * Retrieves the dock's name.
     *
     * @return The dock's name.
     */
    public String getDockName() {
        return dockName;
    }

    /**
     * Sets the dock's name.
     *
     * @param dockName The new name to set for the dock.
     */
    public void setDockName(String dockName) {
        this.dockName = dockName;
    }

    /**
     * Retrieves the image associated with the dock.
     *
     * @return The image URL of the dock.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image associated with the dock.
     *
     * @param image The new image URL to set for the dock.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Retrieves the address of the dock.
     *
     * @return The address of the dock.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the dock.
     *
     * @param address The new address to set for the dock.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
