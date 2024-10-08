package LukaFarkas.MedOpremaBackend.dto;

import LukaFarkas.MedOpremaBackend.entity.PenalPoint;
import LukaFarkas.MedOpremaBackend.entity.RoleEnum;
import LukaFarkas.MedOpremaBackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String FirstName;
    private String LastName;
    private String email;
    private String password;
    private String city;
    private String country;
    private String phone;
    private String profession;
    private RoleEnum userRole;
    private boolean isEnabled = false;
    private List<PenalPointDto> penalPoints;
    private RoleDto role;
    public boolean getIsEnabled(){
        return isEnabled;
    }
}
