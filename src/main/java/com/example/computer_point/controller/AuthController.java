package com.example.computer_point.controller;

import com.example.computer_point.model.ComputerPointUser;
import com.example.computer_point.model.LoginRequest;
import com.example.computer_point.repository.ComputerPointUserRepository;
import com.example.computer_point.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private ComputerPointUserRepository userRepository;
    @Autowired private BCryptPasswordEncoder encoder;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody ComputerPointUser user) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return "❌ Password and Confirm Password do not match!";
        }

        // Optionally check if username or email already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "❌ Username already exists!";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "✅ User registered!";
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        System.out.println("DEBUG username: " + login.getUsername());
        String input = login.getUsername();
        Optional<ComputerPointUser> user = userRepository.findByUsername(input);

        if (user.isEmpty()) {
            user = userRepository.findByEmail(input);
        }

        if (user.isPresent() && encoder.matches(login.getPassword(), user.get().getPassword())) {
            String token = jwtUtil.generateToken(user.get().getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "✅ Login successful");
            response.put("token", token);
            response.put("username", user.get().getUsername());

            return ResponseEntity.ok().body(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "❌ Invalid credentials");
            return ResponseEntity.status(401).body(error);
        }
    }



    @GetMapping("/test")
    public String test() {
        return "🟢 API is reachable!";
    }

}



