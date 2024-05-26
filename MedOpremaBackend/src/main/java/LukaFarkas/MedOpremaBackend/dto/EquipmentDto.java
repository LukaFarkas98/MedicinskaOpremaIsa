package LukaFarkas.MedOpremaBackend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDto {
    private Long equipmentId;
    private String equipmentName;
    private String equipmentType;
    // Add other fields as necessary
}
