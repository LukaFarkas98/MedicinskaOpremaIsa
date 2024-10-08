package LukaFarkas.MedOpremaBackend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private Long id;
    private String companyName;
    private String address;
    private List<EquipmentDto> equipment;
    private Long adminId;  // Added this field
}
