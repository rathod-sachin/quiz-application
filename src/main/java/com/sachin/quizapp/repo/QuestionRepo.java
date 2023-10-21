package com.sachin.quizapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sachin.quizapp.entity.Question;

/**
 * The `QuestionRepo` interface serves as a repository for managing quiz
 * questions in a database. It extends the JpaRepository interface provided by
 * Spring Data JPA, which provides basic CRUD operations and query methods for
 * the `Question` entity.
 * 
 */
@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {

	public List<Question> findByCategory(String category);

	/**
	 * Retrieves a specified number of random questions from a given category.
	 *
	 * @param category          The category of the questions to be retrieved.
	 * @param numberOfQuestions The number of random questions to retrieve.
	 * @return A list of random questions from the specified category.
	 */
	@Query(value = "SELECT * FROM question WHERE category = :category ORDER BY RAND() LIMIT :numberOfQuestions", nativeQuery = true)
	List<Question> findRandomQuestionsByCategory(@Param("category") String category,
			@Param("numberOfQuestions") int numberOfQuestions);
}