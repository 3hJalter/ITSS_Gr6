package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bike {
    private int bikeId;
    private String bikeName;
    private String status;
    private int categoryId;
    private int dockId;
    private String image;
}
