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
                 Integer categoryId, Integer dockId, String image){
        setBikeId(bikeId);
        setBikeName(bikeName);
        setStatus(status);
        this.battery = battery;
        setCategoryId(categoryId);
        setDockId(dockId);
        setImage(image);
    }
}
