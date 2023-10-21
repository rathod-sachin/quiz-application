package com.sachin.quizapp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sachin.quizapp.entity.QuestionWrapper;
import com.sachin.quizapp.entity.Response;
import com.sachin.quizapp.service.QuizService;

/**
 * The `QuizController` class is a RESTful controller that handles HTTP requests
 * related to quizzes. It provides endPoints for creating a quiz, retrieving
 * quiz questions, and submitting quiz responses.
 * 
 * @author Sachin Rathod
 */
@RestController
@RequestMapping(value = "/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	/**
	 * Creates a new quiz with the specified category, number of questions, and
	 * title.
	 * 
	 * @param category          The category of the quiz.
	 * @param numberOfQuestions The number of questions in the quiz.
	 * @param title             The title of the quiz.
	 * @return A ResponseEntity containing a message indicating the result of the
	 *         quiz creation.
	 */
	@PostMapping(value = "/create")
	public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numberOfQuestions,
			@RequestParam String title) {

		return quizService.createQuiz(category, numberOfQuestions, title);
	}

	/**
	 * Retrieves the questions of a quiz identified by its unique identifier.
	 * 
	 * @param id The unique identifier of the quiz.
	 * @return A ResponseEntity containing a list of quiz questions in the form of
	 *         QuestionWrapper objects.
	 */
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Long id) {

		return quizService.getQuizQuestions(id);
	}

	/**
	 * Submits and calculates the result of a quiz identified by its unique
	 * identifier.
	 * 
	 * @param id        The unique identifier of the quiz.
	 * @param responses A list of Response objects containing user responses to quiz
	 *                  questions.
	 * @return A ResponseEntity containing the user's quiz result.
	 */
	@PostMapping(value = "/submit/{id}")
	public ResponseEntity<Long> submitQuiz(@PathVariable Long id, @RequestBody List<Response> responses) {

		return quizService.calculateResult(id, responses);
	}
}