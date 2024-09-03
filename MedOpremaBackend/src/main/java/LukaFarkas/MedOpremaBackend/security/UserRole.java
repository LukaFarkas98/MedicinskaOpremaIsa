package LukaFarkas.MedOpremaBackend.security;

import LukaFarkas.MedOpremaBackend.entity.User;
import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "";
    }
}
