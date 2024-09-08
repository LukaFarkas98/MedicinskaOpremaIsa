package LukaFarkas.MedOpremaBackend.mapper;

import LukaFarkas.MedOpremaBackend.dto.UserDto;
import LukaFarkas.MedOpremaBackend.entity.PenalPoint;
import LukaFarkas.MedOpremaBackend.entity.Role;
import LukaFarkas.MedOpremaBackend.entity.RoleEnum;
import LukaFarkas.MedOpremaBackend.entity.User;

import java.util.List;

import static LukaFarkas.MedOpremaBackend.entity.RoleEnum.*;

public class UserMapper {
    public static UserDto mapToUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getCity(),
                user.getCountry(),
                user.getPhone(),
                user.getProfession(),
                user.getRole().getName(), // Maps the role to RoleEnum
                user.getIsEnabled(),
                PenalPointMapper.toDtoList(user.getPenalPoints()),
                RoleMapper.mapToRoleDto(user.getRole()) // Mapping to RoleDto
        );
    }

    public static User mapToUser(UserDto userDto){
        User user =  new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setId(userDto.getId());
        user.setCity(userDto.getCity());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setCountry(userDto.getCountry());
        user.setPassword(userDto.getPassword());
        user.setIsEnabled(userDto.getIsEnabled());

        Role role = new Role();
        int roleID;
        switch (userDto.getUserRole()) {
            case USER:
                roleID = 1;
                break;
            case ADMIN:
                roleID = 2;
                break;
            case SUPER_ADMIN:
                roleID = 102;
                break;
            default:
                roleID = 0;
        }

        role.setId(roleID);
        role.setName(userDto.getUserRole());

        user.setRole(role);
        List<PenalPoint> penalPoints = PenalPointMapper.toEntityList(userDto.getPenalPoints(), user);
        user.setPenalPoints(penalPoints);
        return user;
    }}








