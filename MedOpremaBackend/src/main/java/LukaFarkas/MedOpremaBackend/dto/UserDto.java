package LukaFarkas.MedOpremaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
