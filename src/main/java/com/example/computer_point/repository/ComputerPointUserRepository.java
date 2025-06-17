package com.example.computer_point.repository;

import com.example.computer_point.model.ComputerPointUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComputerPointUserRepository extends JpaRepository<ComputerPointUser, Long> {
    Optional<ComputerPointUser> findByUsername(String username);
    Optional<ComputerPointUser> findByEmail(String email);
}
