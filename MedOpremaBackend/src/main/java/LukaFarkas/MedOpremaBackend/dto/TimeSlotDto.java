package LukaFarkas.MedOpremaBackend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDto {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private Long id;
    private String startTime;
    private String endTime;
    private Long equipmentId;
    private boolean booked;
    public LocalDateTime getStartTimeAsLocalDateTime() {
        return LocalDateTime.parse(this.startTime, formatter);
    }

    public LocalDateTime getEndTimeAsLocalDateTime() {
        return LocalDateTime.parse(this.endTime, formatter);
    }
}
