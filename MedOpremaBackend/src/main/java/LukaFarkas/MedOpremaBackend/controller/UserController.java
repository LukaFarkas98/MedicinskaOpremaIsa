package LukaFarkas.MedOpremaBackend.controller;

import LukaFarkas.MedOpremaBackend.dto.UserDto;
import LukaFarkas.MedOpremaBackend.entity.RoleEnum;
import LukaFarkas.MedOpremaBackend.mapper.UserMapper;
import LukaFarkas.MedOpremaBackend.repository.UserRepository;
import LukaFarkas.MedOpremaBackend.service.EmailService;
import LukaFarkas.MedOpremaBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    EmailService emailService;



    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private UserService userService;
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody  UserDto userDto){
        UserDto savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/register/superAdmin")
    public ResponseEntity<UserDto> RegisterSuperAdmin(@RequestBody  UserDto userDto) throws InterruptedException {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setEnabled(true);
        userDto.setUserRole(RoleEnum.SUPER_ADMIN);
        UserDto savedUser = userService.createSuperAdmin(userDto);

        // emailService.sendVerificationEmail(UserMapper.mapToUser(savedUser));
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    @PostMapping("/register/admin")
    public ResponseEntity<UserDto> RegisterAdmin(@RequestBody  UserDto userDto) throws InterruptedException {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setEnabled(true);
        userDto.setUserRole(RoleEnum.ADMIN);
        UserDto savedUser = userService.createAdmin(userDto);

       // emailService.sendVerificationEmail(UserMapper.mapToUser(savedUser));
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> RegisterUser(@RequestBody  UserDto userDto) throws InterruptedException {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setEnabled(false);
        userDto.setUserRole(RoleEnum.USER);
        UserDto savedUser = userService.createUser(userDto);

        emailService.sendVerificationEmail(UserMapper.mapToUser(savedUser));
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId){
        UserDto foundUSer = userService.getUserById(userId);

        return new ResponseEntity<>(foundUSer, HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody UserDto updatedUser){
        UserDto userDto = userService.updateUser(userId, updatedUser);
        return new ResponseEntity<>(userDto, HttpStatus.FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("User with ID:" + userId + "has succesfully been deleted", HttpStatus.OK);
    }
}
