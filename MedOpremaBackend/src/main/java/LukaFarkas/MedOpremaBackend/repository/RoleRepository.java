package LukaFarkas.MedOpremaBackend.repository;

import LukaFarkas.MedOpremaBackend.entity.Role;
import LukaFarkas.MedOpremaBackend.entity.RoleEnum;
import org.springframework.data.repository.CrudRepository;


import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
