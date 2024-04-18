package com.example.demo.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.SubjectRepository;
import com.example.demo.model.Question;
import com.example.demo.model.Subject;
import com.example.demo.service.QuestionService;
@Service
public class QuestionServiceImplementation implements QuestionService {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Override
	public void addQuestion(Question question) {
		questionRepository.save(question);
	}
	
	@Override
	public Question getByQuestionId(long questionId) {
		Question question = questionRepository.getById(questionId);
		return question;
	}
	
	@Override
	public List<Question> findAllQuestions(long subjectId) {
		List<Question> questionList = questionRepository.findBySubjectId(subjectId);
		return questionList;
	}
	
	@Override
	public void deleteQuestion(long questionId) {
		questionRepository.deleteById(questionId);
		
		
	}
	
	@Override
	public void updateQuestion(Question question) {
		questionRepository.save(question);
		
	}
	 

}
