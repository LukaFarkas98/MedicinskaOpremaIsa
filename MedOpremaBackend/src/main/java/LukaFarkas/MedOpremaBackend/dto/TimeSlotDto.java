package LukaFarkas.MedOpremaBackend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDto {
    private Long id;
    private String startTime;
    private String endTime;
    private Long equipmentId;
}
