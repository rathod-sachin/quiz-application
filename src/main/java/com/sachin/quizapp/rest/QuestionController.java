package com.sachin.quizapp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sachin.quizapp.entity.Question;
import com.sachin.quizapp.service.QuestionService;

/**
 * The `QuestionController` class is a RESTful controller that handles HTTP
 * requests related to quiz questions. It defines several endPoints for
 * performing CRUD (Create, Read, Update, Delete) operations on questions.
 * 
 * @author Sachin Rathod 
 */
@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	/**
	 * Retrieves all the questions available in the system.
	 * 
	 * @return A ResponseEntity containing a list of questions and an HTTP status
	 *         code.
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<List<Question>> getAllQuestions() {

		return questionService.getAllQuestions();
	}

	/**
	 * Retrieves questions by a specific category.
	 * 
	 * @param category The category for which questions should be retrieved.
	 * @return A ResponseEntity containing a list of questions matching the category
	 *         and an HTTP status code.
	 */
	@GetMapping(value = "category/{category}")
	public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {

		return questionService.getQuestionsByCategory(category);
	}

	/**
	 * Adds a new question to the system.
	 * 
	 * @param question The question object to be added.
	 * @return A ResponseEntity containing the added question and an HTTP status
	 *         code.
	 */
	@PostMapping(value = "/add")
	public ResponseEntity<Question> addQuestion(@RequestBody Question question) {

		return questionService.addQuestion(question);
	}

	/**
	 * Updates an existing question in the system.
	 * 
	 * @param id       The unique identifier of the question to be updated.
	 * @param question The updated question object.
	 * @return A ResponseEntity containing the updated question and an HTTP status
	 *         code.
	 */
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
		return questionService.updateQuestion(id, question);
	}

	/**
	 * Deletes a question from the system by its unique identifier.
	 * 
	 * @param id The unique identifier of the question to be deleted.
	 */
	@DeleteMapping(value = "/delete/{id}")
	public void deleteQuestion(@PathVariable Long id) {

		questionService.deleteQuestion(id);
	}
}