package LukaFarkas.MedOpremaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PenalPointDto {
    private Long id;
    private Long userId;
    private int points;
    private LocalDateTime timestamp;
}