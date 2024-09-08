package LukaFarkas.MedOpremaBackend.dto;

import LukaFarkas.MedOpremaBackend.entity.RoleEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleDto {
    private Integer id;
    private String name;  // You can use String if you want to return the enum as a string.
    private String description;



}
