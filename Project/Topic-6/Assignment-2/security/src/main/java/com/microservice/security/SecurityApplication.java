package com.microservice.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController()
@EnableWebSecurity
public class SecurityApplication {
	//https://www.codejava.net/frameworks/spring-boot/spring-boot-security-authentication-with-jpa-hibernate-and-mysql
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}
	
	@GetMapping()
	public String greetings(Authentication auth) {
		User pr=  (User) auth.getPrincipal();		
		return "Hello !!";
	}

}
