package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Submission;

public interface SubmissionService {
	void addSubmission(Submission submission);
	Submission findByStudentIdQuizId(long studentId,long quizId);
	String findByStudentIdAndQuizId(long studentId,long quizId);
	List<Submission> findAllByStudentIdQuizId(long studentId,long quizId);
	Submission findByStudentId(long studentId);
	String getByQuestionIdAndStudentId(Long questionId, Long studentId, Long quizId);
}
