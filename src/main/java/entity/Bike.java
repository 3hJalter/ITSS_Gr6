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
    private Integer bikeId;
    private String bikeName;
    private String status;
    private Category category;
    private Dock dock;
    private String image;

    public String convertToString() {
        return "Bike{" +
                "bikeId=" + bikeId +
                ", bikeName='" + bikeName + '\'' +
                ", status='" + status + '\'' +
                ", category=" + category +
                ", dock=" + dock +
                ", image='" + image + '\'' +
                '}';
    }
}
