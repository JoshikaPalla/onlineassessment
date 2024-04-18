package com.example.demo.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.QuizRepository;
import com.example.demo.Repository.SubjectRepository;
import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.model.Subject;
import com.example.demo.service.QuizService;

@Service
public class QuizServiceImplementation implements QuizService {
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Override
	public void addQuiz(Quiz quiz) {
		quizRepository.save(quiz);
	}

	@Override
	public Quiz getByQuizId(long quizId) {
		Quiz quiz = quizRepository.getById(quizId);
		return quiz;
	}

	@Override
	public List<Quiz> findAllQuizByInstructorId(long instructorId) {
		List<Quiz> quizList = quizRepository.getByInstructorId(instructorId);
		return quizList;
	}
	
	@Override
	public List<Quiz> findAllQuizByStudentId(long studentId) {
		List<Quiz> quizList = quizRepository.getByInstructorId(studentId);
		return quizList;
	}

	@Override
	public void deleteQuiz(long quizId) {
		quizRepository.deleteById(quizId);
		
		
	}

	@Override
	public void updateQuiz(Quiz quiz) {
		quizRepository.save(quiz);
	}

	
}
