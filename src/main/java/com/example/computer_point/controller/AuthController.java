package com.example.computer_point.controller;

import com.example.computer_point.model.ComputerPointUser;
import com.example.computer_point.repository.ComputerPointUserRepository;
import com.example.computer_point.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public String login(@RequestBody ComputerPointUser login) {
        var user = userRepository.findByUsername(login.getUsername());
        if (user.isPresent() && encoder.matches(login.getPassword(), user.get().getPassword())) {
            return jwtUtil.generateToken(login.getUsername());
        } else {
            return "❌ Invalid credentials";
        }
    }

    @GetMapping("/test")
    public String test() {
        return "🟢 API is reachable!";
    }

}



