package com.example.demo.Repository;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Submission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long> {
	List<Submission> findByStudentId(long studentId);
	Submission findByQuizId(long quizId);
	List<Submission> findByStudentIdAndQuizId(long studentId,long quizId);
	List<Submission> findByQuestionIdAndStudentIdAndQuizId(Long questionId, Long studentId, Long quizId);
}
