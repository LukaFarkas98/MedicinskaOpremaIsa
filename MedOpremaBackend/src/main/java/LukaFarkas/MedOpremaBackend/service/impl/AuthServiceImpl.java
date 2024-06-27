package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.entity.User;
import LukaFarkas.MedOpremaBackend.repository.UserRepository;
import LukaFarkas.MedOpremaBackend.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    private UserRepository userRepository;



    @Override
    public String authenticate(String username, String password) {
        // Example logic to authenticate user
        User user = userRepository.findByEmail(username);
        if (user != null && user.getPassword().equals(password)) {
            // Generate and return JWT token or session token
            return generateToken(username);
        }
        return null; // Authentication failed
    }

    private String generateToken(String username) {
        // Example method to generate JWT token using a library like JJwt
        // This is a simplified example; in practice, you should use a library to generate tokens securely
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(key)
                .compact();
    }
}
