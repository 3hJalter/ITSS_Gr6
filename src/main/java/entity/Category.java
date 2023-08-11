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
    private int categoryId;
    private String categoryName;
    private double price;
    private float priceMultiple;
}
