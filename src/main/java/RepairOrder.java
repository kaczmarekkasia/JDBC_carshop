import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairOrder {
    private Long id;
    private LocalDateTime addDate;
    private boolean isDone;
    private LocalDateTime endDate;
    private String orderContent;
    private Long carId;
}
