package com.sachin.quizapp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sachin.quizapp.entity.QuestionWrapper;
import com.sachin.quizapp.entity.Response;

/**
 * The `QuizService` interface defines the contract for services related to
 * managing quizzes and quiz questions. It specifies methods for creating
 * quizzes, retrieving quiz questions, and calculating quiz results.
 */
public interface QuizService {

	public ResponseEntity<String> createQuiz(String category, int numberOfQuestions, String title);

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Long id);

	public ResponseEntity<Long> calculateResult(Long id, List<Response> responses);
}