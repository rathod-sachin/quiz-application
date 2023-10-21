package com.sachin.quizapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sachin.quizapp.entity.Quiz;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Long>{
		
}