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
public class Question {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long questionId;
	
	@Column
	private String questionTitle;
	
	@Column
	private String option1;
	
	@Column
	private String option2;
	
	@Column
	private String option3;
	
	@Column
	private String option4;
	
	@Column
	private String correctAnswer;
	
	@Column
	private String difficultyLevel;

	@Column
	private long subjectId;
	
	@Column
	private long instructorId;
	
	@Column
	private int marks;
	
	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}
	
	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	
	
	public long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(long instructorId) {
		this.instructorId = instructorId;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}
	
	
	public Question() {
		super();
	}
	public Question(String questionTitle, String option1, String option2, String option3, String option4,
			String correctAnswer, String difficultyLevel, long subjectId, long instructorId, int marks) {
		super();
		this.questionTitle = questionTitle;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.correctAnswer = correctAnswer;
		this.difficultyLevel = difficultyLevel;
		this.subjectId = subjectId;
		this.instructorId = instructorId;
		this.marks = marks;
	}
	
	public Question(String questionTitle, String option1, String option2, String option3, String option4,
			String correctAnswer, String difficultyLevel, int marks) {
		super();
		this.questionTitle = questionTitle;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.correctAnswer = correctAnswer;
		this.difficultyLevel = difficultyLevel;
		this.marks = marks;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionTitle=" + questionTitle + ", option1=" + option1
				+ ", option2=" + option2 + ", option3=" + option3 + ", option4=" + option4 + ", correctAnswer="
				+ correctAnswer + ", difficultyLevel=" + difficultyLevel + ", subjectId=" + subjectId
				+ ", instructorId=" + instructorId + ", marks=" + marks + "]";
	}

	
	
}
