package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Question;


public interface QuestionService {
	
	void addQuestion(Question question);
	Question getByQuestionId(long questionId);
	List<Question> findAllQuestions(long subjectId);
	
	void deleteQuestion(long questionId);
	void updateQuestion(Question question);
	
}
