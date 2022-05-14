package com.example.customanimedatabase.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.customanimedatabase.model")
@EntityScan("com.example.customanimedatabase.model")
@ComponentScan("com.example.customanimedatabase")
@SpringBootApplication
public class CustomAnimeDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomAnimeDatabaseApplication.class, args);
	}

}
