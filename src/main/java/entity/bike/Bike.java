package entity.bike;

import entity.Category;
import entity.Dock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bike {
    private Integer bikeId;
    private String bikeName;
    private Category category;
    private Dock dock;
    private String image;
    private UUID barcode;
    public String convertToString() {
        return "Bike{" +
                "bikeId=" + bikeId +
                ", bikeName='" + bikeName + '\'' +
                ", category=" + category +
                ", dock=" + dock +
                ", image='" + image + '\'' +
                ", barcode='" + barcode + '\'' +
                '}';
    }
}
