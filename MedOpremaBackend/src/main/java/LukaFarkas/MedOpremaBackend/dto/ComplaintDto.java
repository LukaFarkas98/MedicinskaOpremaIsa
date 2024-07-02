package LukaFarkas.MedOpremaBackend.dto;

import LukaFarkas.MedOpremaBackend.entity.Complaint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDto {
    private Long userId;
    private Long companyId;
    private Long adminId;
    private String details;

    // Getters and Setters
}

