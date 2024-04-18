package com.example.demo.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.SubmissionRepository;
import com.example.demo.model.Question;
import com.example.demo.model.Submission;
import com.example.demo.service.SubmissionService;

@Service
public class SubmissionServiceImplementation implements SubmissionService{
	
	@Autowired
	private SubmissionRepository submissionRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Override
	public void addSubmission(Submission submission) {
		submissionRepository.save(submission);
	}

	@Override
	public Submission findByStudentIdQuizId(long studentId, long quizId) {
		List<Submission> submissionList = submissionRepository.findByStudentIdAndQuizId(studentId,quizId);
		System.out.println(submissionList);
		if(submissionList.isEmpty()) {
			return null;
		}else {
			return submissionList.get(0);
		}
	}
	
	@Override
	public String findByStudentIdAndQuizId(long studentId, long quizId) {
		if(submissionRepository.findByStudentIdAndQuizId(studentId, quizId).isEmpty()) {
			return "Not Attempted";
		}else {
			List<Submission> submissionList = submissionRepository.findByStudentIdAndQuizId(studentId, quizId);
			double totalMarks = 0;
			double securedMarks = 0;
			for(Submission submission: submissionList) {
				Question question = questionRepository.getById(submission.getQuestionId());
				totalMarks += question.getMarks();
//				if(submission.getSelectedOption()==null) {
//					submission.setSelectedOption("not selected");
//				}
				System.out.println(submission.getSelectedOption()+"\n" + question.getCorrectAnswer());
				if(submission.getSelectedOption().equals(question.getCorrectAnswer())){
					
					securedMarks += question.getMarks();
					System.out.println(question.getMarks());
				}
				System.out.println(securedMarks);
			}
			System.out.println(securedMarks);
			System.out.println(totalMarks);
			double performance = (securedMarks/totalMarks)*100;
			return ""+performance;
		}
	}

	@Override
	public Submission findByStudentId(long studentId) {
		List<Submission> submission = submissionRepository.findByStudentId(studentId);
		if(submission.isEmpty()) {
			return null;
		}
		return submission.get(0);
	}
	
	@Override
	public String getByQuestionIdAndStudentId(Long questionId,Long studentId,Long quizId) {
		List<Submission> submissions = submissionRepository.findByQuestionIdAndStudentIdAndQuizId(questionId,studentId,quizId);
		if(!submissions.isEmpty()) {
			return submissions.get(0).getSelectedOption();
		}
		return "Not Attempted";
	}

	@Override
	public List<Submission> findAllByStudentIdQuizId(long studentId, long quizId) {
		
		return submissionRepository.findByStudentIdAndQuizId(studentId, quizId);
	}
}
