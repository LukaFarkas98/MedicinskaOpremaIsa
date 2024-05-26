package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.dto.UserDto;
import LukaFarkas.MedOpremaBackend.entity.User;
import LukaFarkas.MedOpremaBackend.exception.ResourceNotFoundException;
import LukaFarkas.MedOpremaBackend.mapper.UserMapper;
import LukaFarkas.MedOpremaBackend.repository.UserRepository;
import LukaFarkas.MedOpremaBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final JdbcTemplate jdbcTemplate;
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User userFound = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id:" + userId + " doesnt exist!!" ));
        return UserMapper.mapToUserDto(userFound);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long userId, UserDto updatedUser) {
        User foundUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(("User with given id:" + userId + " doesnt exist!!" ))
        );

        foundUser.setFirstName(updatedUser.getFirstName());
        foundUser.setLastName(updatedUser.getLastName());
        foundUser.setEmail(updatedUser.getEmail());
        foundUser.setPassword(updatedUser.getPassword());
        foundUser.setCity(updatedUser.getCity());
        foundUser.setCountry(updatedUser.getCountry());
        foundUser.setPhone(updatedUser.getPhone());
        foundUser.setProfession(updatedUser.getProfession());

        User newpUdatedUser = userRepository.save(foundUser);
        return UserMapper.mapToUserDto(newpUdatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(("User with given id:" + userId + " doesnt exist!!" ))
        );
        userRepository.deleteById(userId);
    }
}
