package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Submission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long submissionId;
	
	
	@Column
	private long quizId;
	
	
	@Column
	private long questionId;
	
	@Column
	private String selectedOption;
	
	@Column
	private long studentId;

	public long getQuizId() {
		return quizId;
	}

	public void setQuizId(long quizId) {
		this.quizId = quizId;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	
	public Submission() {
		super();
	}

	public Submission(long quizId, long questionId, String selectedOption, long studentId) {
		super();
		this.quizId = quizId;
		this.questionId = questionId;
		this.selectedOption = selectedOption;
		this.studentId = studentId;
	}

	@Override
	public String toString() {
		return "Submission [submissionId=" + submissionId + ", quizId=" + quizId + ", questionId=" + questionId
				+ ", selectedOption=" + selectedOption + ", studentId=" + studentId + "]";
	}
	

}
