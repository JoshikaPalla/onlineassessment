package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.model.Subject;

public interface QuizService {
	void addQuiz(Quiz quiz);
	Quiz getByQuizId(long quizId);
	List<Quiz> findAllQuizByInstructorId(long instructorId);
	List<Quiz> findAllQuizByStudentId(long studentId);
	void deleteQuiz(long quizId);
	void updateQuiz(Quiz quiz);
	
}
