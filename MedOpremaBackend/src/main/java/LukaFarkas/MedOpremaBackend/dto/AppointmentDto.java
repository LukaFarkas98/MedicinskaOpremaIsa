package LukaFarkas.MedOpremaBackend.dto;


import LukaFarkas.MedOpremaBackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
    private Long appointmentId;
    private Long equipmentId;
    private Long companyId;
    private Long userId;
    private Long timeSlotId;
    private Long quantity;
}
