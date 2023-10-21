package com.sachin.quizapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The `QuizappApplication` class serves as the entry point for the Quiz
 * Application. It is the main class that Spring Boot uses to start the
 * application.
 *
 * The `@SpringBootApplication` annotation indicates that this class is a Spring
 * Boot application. It enables auto-configuration and component scanning for
 * the application.
 *
 * @author Sachin Rathod
 */
@SpringBootApplication
public class QuizappApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizappApplication.class, args);
	}
}