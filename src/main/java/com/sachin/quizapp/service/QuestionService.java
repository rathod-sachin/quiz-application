package com.sachin.quizapp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sachin.quizapp.entity.Question;

/**
 * The `QuestionService` interface defines the contract for services related to
 * managing quiz questions. It specifies methods for retrieving, adding,
 * updating, and deleting questions, as well as getting questions by category.
 *
 */
public interface QuestionService {

	public ResponseEntity<List<Question>> getAllQuestions();

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category);

	public ResponseEntity<Question> addQuestion(Question question);

	ResponseEntity<Question> updateQuestion(Long id, Question updatedQuestion);

	ResponseEntity<Void> deleteQuestion(Long id);
}