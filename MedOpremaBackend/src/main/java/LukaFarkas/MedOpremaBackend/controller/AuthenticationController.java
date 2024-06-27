package LukaFarkas.MedOpremaBackend.controller;

import LukaFarkas.MedOpremaBackend.entity.User;
import LukaFarkas.MedOpremaBackend.repository.UserRepository;
import LukaFarkas.MedOpremaBackend.service.AuthService;
import LukaFarkas.MedOpremaBackend.service.impl.CustomUserDetailsService;
import LukaFarkas.MedOpremaBackend.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        int a = 1 + 2;
        String token = authService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        if (token != null) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
            final String jwt = JwtUtil.generateToken(userDetails);

            User user = userRepository.findByEmail(authenticationRequest.getEmail());
            return ResponseEntity.ok(new JwtResponse(jwt, user));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
