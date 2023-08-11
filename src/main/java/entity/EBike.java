package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EBike extends Bike {
    private Integer battery;
    public EBike(Integer bikeId, String bikeName, String status, Integer battery,
                 Category category, Dock dock, String image){
        setBikeId(bikeId);
        setBikeName(bikeName);
        setStatus(status);
        this.battery = battery;
        setCategory(category);
        setDock(dock);
        setImage(image);
    }

    public String convertToString() {
        return "EBike{" +
                "bikeId=" + getBikeId() +
                ", bikeName='" + getBikeName() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", battery=" + battery +
                ", category=" + getCategory() +
                ", dock=" + getDock() +
                ", image='" + getImage() + '\'' +
                '}';
    }
}
