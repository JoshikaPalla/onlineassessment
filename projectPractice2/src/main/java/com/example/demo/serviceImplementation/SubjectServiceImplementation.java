package com.example.demo.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.SubjectRepository;
import com.example.demo.model.Subject;
import com.example.demo.service.SubjectService;

@Service
public class SubjectServiceImplementation implements SubjectService{
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Override
	public void addSubject(Subject subject) {
		subjectRepository.save(subject);
	}

	@Override
	public Subject getBySubjectId(long subjectId) {
		Subject subject = subjectRepository.getById(subjectId);
		return subject;
	}

	@Override
	public List<Subject> findAllSubjects() {
		List<Subject> subjectList = subjectRepository.findAll();
		return subjectList;
	}

	@Override
	public void deleteSubject(long subjectId) {
		subjectRepository.deleteById(subjectId);
		
		
	}

	@Override
	public void updateSubject(Subject subject) {
		subjectRepository.save(subject);
	}

	

}
