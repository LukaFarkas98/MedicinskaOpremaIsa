package LukaFarkas.MedOpremaBackend.dto;

import LukaFarkas.MedOpremaBackend.entity.Complaint;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComplaintDto {
    private Long id;
    private Long userId;
    private Long companyId;
    private Long adminId;
    private String details;
    private LocalDateTime date;

    // Getters and Setters
}

