package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dock {
    private Integer dockId;
    private String dockName;
    private String image;
    private String address;

    public String convertToString() {
        return "Dock{" +
                "dockId=" + dockId +
                ", dockName='" + dockName + '\'' +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
