package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {
    private Integer categoryId;
    private String categoryName;
    private Long bikePrice;
    private Double depositRate;
    private Long rentPrice;
    private Double priceMultiple;

    public String convertToString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", bikePrice=" + bikePrice +
                ", deposit=" + depositRate +
                ", rentPrice=" + rentPrice +
                ", priceMultiple=" + priceMultiple +
                '}';
    }
}
