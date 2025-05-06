package com.example.computer_point;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.computer_point")
public class ComputerPointApplication {
	public static void main(String[] args) {
		SpringApplication.run(ComputerPointApplication.class, args);
	}
}
