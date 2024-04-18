package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Subject;

public interface SubjectService {
	void addSubject(Subject subject);
	Subject getBySubjectId(long subjectId);
	List<Subject> findAllSubjects();
	void deleteSubject(long subjectId);
	void updateSubject(Subject subject);
	
}
