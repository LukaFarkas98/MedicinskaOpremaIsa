package LukaFarkas.MedOpremaBackend.mapper;

import LukaFarkas.MedOpremaBackend.dto.UserDto;
import LukaFarkas.MedOpremaBackend.entity.PenalPoint;
import LukaFarkas.MedOpremaBackend.entity.User;

import java.util.List;

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
                PenalPointMapper.toDtoList(user.getPenalPoints())
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
        List<PenalPoint> penalPoints = PenalPointMapper.toEntityList(userDto.getPenalPoints(), user);
        user.setPenalPoints(penalPoints);
        return user;
    }
}


