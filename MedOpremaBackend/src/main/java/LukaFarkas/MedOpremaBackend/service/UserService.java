package LukaFarkas.MedOpremaBackend.service;

import LukaFarkas.MedOpremaBackend.dto.UserDto;
import LukaFarkas.MedOpremaBackend.entity.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto createSuperAdmin(UserDto userDto);

    UserDto createAdmin(UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(Long userId, UserDto updatedUser);

    void deleteUser(Long userId);
}