package dock;

public class Dock {
    private Integer dockId;
    private String dockName;
    private String image;
    private String address;

    public Dock(Integer dockId, String dockName, String image, String address) {
        this.dockId = dockId;
        this.dockName = dockName;
        this.image = image;
        this.address = address;
    }

    public Integer getDockId() {
        return dockId;
    }

    public void setDockId(Integer dockId) {
        this.dockId = dockId;
    }

    public String getDockName() {
        return dockName;
    }

    public void setDockName(String dockName) {
        this.dockName = dockName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
