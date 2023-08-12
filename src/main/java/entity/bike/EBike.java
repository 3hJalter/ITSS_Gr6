package entity.bike;

import entity.Category;
import entity.Dock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class EBike extends Bike {
    private Integer battery;
    public EBike(Integer bikeId, String bikeName, Integer battery,
                 Category category, Dock dock, String image, UUID barcode){
        setBikeId(bikeId);
        setBikeName(bikeName);
        this.battery = battery;
        setCategory(category);
        setDock(dock);
        setImage(image);
        setBarcode(barcode);
    }

    @Override
    public String convertToString() {
        return "EBike{" +
                "bikeId=" + getBikeId() +
                ", bikeName='" + getBikeName() + '\'' +
                ", battery=" + battery +
                ", category=" + getCategory() +
                ", dock=" + getDock() +
                ", image='" + getImage() + '\'' +
                '}';
    }
}
