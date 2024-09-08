package LukaFarkas.MedOpremaBackend.mapper;

import LukaFarkas.MedOpremaBackend.dto.RoleDto;
import LukaFarkas.MedOpremaBackend.entity.Role;
import LukaFarkas.MedOpremaBackend.entity.RoleEnum;

public class RoleMapper {

    // Convert Role entity to RoleDto
    public static RoleDto mapToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName().name());  // Convert Enum to String
        roleDto.setDescription(role.getDescription());

        return roleDto;
    }

    // Convert RoleDto to Role entity
    public static Role mapToRole(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(RoleEnum.valueOf(roleDto.getName()));  // Convert String back to Enum
        role.setDescription(roleDto.getDescription());

        return role;
    }
}
