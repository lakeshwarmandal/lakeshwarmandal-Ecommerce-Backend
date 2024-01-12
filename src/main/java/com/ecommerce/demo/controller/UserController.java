package com.ecommerce.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.demo.service.UserService;
import com.ecommerce.demo.model.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private final String SECRET_KEY = "ww-xcxnbs-12211"; 

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        User existingUser = userService.getUserById(id);
        if (existingUser != null) {
            user.setId(id);
            User updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        User existingUser = userService.getUserById(id);
        if (existingUser != null) {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/changepassword")
    public ResponseEntity<Void> changePassword(@PathVariable String id, @RequestParam String newPassword) {
        userService.changePassword(id, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

   @PostMapping("/login")
public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user, HttpServletResponse response) {
    try {
        User existingUser = userService.getUserByUsername(user.getUserName());

        if (existingUser != null) {
            if (existingUser.getPassword().equals(user.getPassword())) {
                // Generate JWT token
                String token = generateToken(existingUser.getUserName());

                // Add the token to the response headers
                response.addHeader("Authorization", "Bearer " + token);

                // Create the response body
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("username", existingUser.getUserName());
                responseBody.put("authorization", "Bearer " + token);
                responseBody.put("userId", existingUser.getId());
                responseBody.put("role", existingUser.getRole()); // Add the role information

                // Login successful
                return ResponseEntity.ok(responseBody);
            } else {
                // Invalid password
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Invalid password"));
            }
        } else {
            // User not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "User not found"));
        }
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("error", "Internal Server Error"));
    }
}



    private String generateToken(String username) {
    try {
        // Set token expiration time (e.g., 1 hour)
        long expirationTime = 3600000; // 1 hour in milliseconds

        // Generate a secure signing key
        Key signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Set JWT claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        // Build JWT
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(signingKey);

        // Generate token
        String token = builder.compact();

        // Print token for debugging
        System.out.println("Generated token: " + token);

        return token;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
}
