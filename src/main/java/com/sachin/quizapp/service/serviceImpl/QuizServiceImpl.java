package com.sachin.quizapp.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sachin.quizapp.entity.Question;
import com.sachin.quizapp.entity.QuestionWrapper;
import com.sachin.quizapp.entity.Quiz;
import com.sachin.quizapp.entity.Response;
import com.sachin.quizapp.repo.QuestionRepo;
import com.sachin.quizapp.repo.QuizRepo;
import com.sachin.quizapp.service.QuizService;

/**
 * The `QuizServiceImpl` class is an implementation of the `QuizService`
 * interface. It provides methods for creating quizzes, retrieving quiz
 * questions, and calculating quiz results.
 */
@Service
public class QuizServiceImpl implements QuizService {

	private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);

	@Autowired
	private QuizRepo quizRepo;

	@Autowired
	private QuestionRepo questionRepo;

	/**
	 * Creates a new quiz with random questions from a specified category, a given
	 * number of questions, and a title.
	 * 
	 * @param category          The category of the quiz.
	 * @param numberOfQuestions The number of questions in the quiz.
	 * @param title             The title of the quiz.
	 * @return A ResponseEntity containing a message indicating the result of the
	 *         quiz creation.
	 */
	@Override
	public ResponseEntity<String> createQuiz(String category, int numberOfQuestions, String title) {

		try {
			// Fetch a list of random questions from the specified category
			List<Question> questions = questionRepo.findRandomQuestionsByCategory(category, numberOfQuestions);

			// Create a new quiz and associate it with the selected questions
			Quiz quiz = new Quiz();
			quiz.setTitle(title);
			quiz.setQuestions(questions);

			quizRepo.save(quiz);

			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Failed to create quiz", e);
			return new ResponseEntity<>("Failed to create quiz", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves the questions of a quiz identified by its unique identifier.
	 * 
	 * @param id The unique identifier of the quiz.
	 * @return A ResponseEntity containing a list of quiz questions in the form of
	 *         QuestionWrapper objects.
	 */
	@Override
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Long id) {

		try {
			// Retrieve the quiz from the database based on its unique identifier
			Optional<Quiz> quiz = quizRepo.findById(id);

			if (quiz.isPresent()) {
				// Extract questions associated with the quiz and format them as QuestionWrapper
				// objects
				List<Question> questionsFromDB = quiz.get().getQuestions();
				List<QuestionWrapper> questionsForUser = new ArrayList<>();

				for (Question question : questionsFromDB) {

					// Create a QuestionWrapper for each question
					QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(), question.getQuestionTitle(),
							question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());

					questionsForUser.add(questionWrapper);
				}

				return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
			} else {
				logger.warn("Quiz not found for ID={}", id);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Failed to retrieve quiz questions", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Calculates the result of a quiz based on user responses.
	 * 
	 * @param id        The unique identifier of the quiz.
	 * @param responses A list of Response objects containing user responses to quiz
	 *                  questions.
	 * @return A ResponseEntity containing the user's quiz result.
	 */
	@Override
	public ResponseEntity<Long> calculateResult(Long id, List<Response> responses) {

		try {
			// Retrieve the quiz from the database based on its ID
			Optional<Quiz> quiz = quizRepo.findById(id);

			if (quiz.isPresent()) {
				// Extract questions associated with the quiz
				List<Question> questions = quiz.get().getQuestions();
				long correctResponses = 0;

				// Compare user responses with correct answers to calculate the score
				for (int i = 0; i < questions.size() && i < responses.size(); i++) {
					if (responses.get(i).getResponse().equals(questions.get(i).getCorrectAnswer())) {
						correctResponses++;
					}
				}

				logger.info("Calculated quiz result for quiz ID={}", id);
				return new ResponseEntity<>(correctResponses, HttpStatus.OK);
			} else {
				logger.warn("Quiz not found for ID={}", id);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Failed to calculate quiz result", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}