import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private Long id;
    private String regNr;
    private Long millage;
    private String brandModel;
    private Integer yearOfProduction;
    private CarType carType;
    private String ownerName;
}
