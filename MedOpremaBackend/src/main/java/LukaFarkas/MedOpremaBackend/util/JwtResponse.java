package LukaFarkas.MedOpremaBackend.util;

import LukaFarkas.MedOpremaBackend.dto.UserDto;
import LukaFarkas.MedOpremaBackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private  String token;

    private UserDto user;

}