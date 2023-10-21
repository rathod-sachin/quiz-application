package com.sachin.quizapp.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sachin.quizapp.entity.Question;
import com.sachin.quizapp.repo.QuestionRepo;
import com.sachin.quizapp.service.QuestionService;

/**
 * The QuestionServiceImpl class is an implementation of the QuestionService
 * interface. It provides methods for performing CRUD operations on quiz
 * questions.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

	private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

	@Autowired
	private QuestionRepo questionRepo;

	/**
	 * Retrieves all questions available in the database.
	 * 
	 * @return A ResponseEntity containing a list of questions and an HTTP status
	 *         code.
	 */
	@Override
	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			// Fetch all questions from the database
			List<Question> questions = questionRepo.findAll();
			return ResponseEntity.ok(questions);
		} catch (Exception e) {
			logger.error("Failed to fetch all questions", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves questions by a specific category.
	 * 
	 * @param category The category for which questions should be retrieved.
	 * @return A ResponseEntity containing a list of questions matching the category
	 *         and an HTTP status code.
	 */
	@Override
	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {

		try {
			// Retrieve questions by the specified category
			List<Question> questions = questionRepo.findByCategory(category);

			if (questions.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return ResponseEntity.ok(questions);
		} catch (Exception e) {
			logger.error("Failed to fetch questions by category: " + category, e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Adds a new question to the database.
	 * 
	 * @param question The question object to be added.
	 * @return A ResponseEntity containing the added question and an HTTP status
	 *         code.
	 */
	@Override
	public ResponseEntity<Question> addQuestion(Question question) {

		try {
			if (question == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			Question savedQuestion = questionRepo.save(question);
			return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Failed to add a question", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Deletes a question from the system by its ID.
	 * 
	 * @param id The unique identifier of the question to be deleted.
	 * @return A ResponseEntity with an HTTP status code indicating the result of
	 *         the deletion operation.
	 */
	@Override
	public ResponseEntity<Void> deleteQuestion(Long id) {

		try {
			// Check if the question with the specified ID exists
			Optional<Question> existingQuestion = questionRepo.findById(id);

			if (existingQuestion.isPresent()) {
				questionRepo.deleteById(id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Failed to delete the question with ID: " + id, e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updates an existing question in the database.
	 * 
	 * @param id              The unique identifier of the question to be updated.
	 * @param updatedQuestion The updated question object.
	 * @return A ResponseEntity containing the updated question and an HTTP status
	 *         code.
	 */
	@Override
	public ResponseEntity<Question> updateQuestion(Long id, Question updatedQuestion) {

		try {
			// Check if the question with the specified ID exists
			Optional<Question> existingQuestion = questionRepo.findById(id);

			// Update the fields of the existing question with the new values
			if (existingQuestion.isPresent()) {
				Question questionToUpdate = existingQuestion.get();

				if (updatedQuestion.getQuestionTitle() != null) {
					questionToUpdate.setQuestionTitle(updatedQuestion.getQuestionTitle());
				}
				if (updatedQuestion.getOption1() != null) {
					questionToUpdate.setOption1(updatedQuestion.getOption1());
				}
				if (updatedQuestion.getOption2() != null) {
					questionToUpdate.setOption2(updatedQuestion.getOption2());
				}
				if (updatedQuestion.getOption3() != null) {
					questionToUpdate.setOption3(updatedQuestion.getOption3());
				}
				if (updatedQuestion.getOption4() != null) {
					questionToUpdate.setOption4(updatedQuestion.getOption4());
				}
				if (updatedQuestion.getCorrectAnswer() != null) {
					questionToUpdate.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
				}
				if (updatedQuestion.getCategory() != null) {
					questionToUpdate.setCategory(updatedQuestion.getCategory());
				}
				if (updatedQuestion.getDifficultyLevel() != null) {
					questionToUpdate.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
				}

				// Save the updated question in the database
				Question savedQuestion = questionRepo.save(questionToUpdate);
				return new ResponseEntity<>(savedQuestion, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Failed to update the question with ID: " + id, e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}