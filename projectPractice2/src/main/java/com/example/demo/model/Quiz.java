package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long quizId;
	
	@Column
	private String quizName;
	
	@Column
	private long instructorId;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "quiz_questions",
			joinColumns = @JoinColumn(
					name = "quizId",
					referencedColumnName = "quizId"
			),
	
			inverseJoinColumns = @JoinColumn(
					name="questionId",
					referencedColumnName = "questionId"
					)
			)
	private List<Question> questionList;

	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "quiz_students",
			joinColumns = @JoinColumn(
					name = "quizId",
					referencedColumnName = "quizId"
			),
	
			inverseJoinColumns = @JoinColumn(
					name="studentId",
					referencedColumnName = "studentId"
					)
			)
	private List<Student> studentsList;
	
	public long getQuizId() {
		return quizId;
	}


	public void setQuizId(long quizId) {
		this.quizId = quizId;
	}


	public String getQuizName() {
		return quizName;
	}


	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}


	public long getInstructorId() {
		return instructorId;
	}


	public void setInstructorId(long instructorId) {
		this.instructorId = instructorId;
	}


	public List<Question> getQuestionList() {
		return questionList;
	}


	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}


	public List<Student> getStudentsList() {
		return studentsList;
	}


	public void setStudentsList(List<Student> studentsList) {
		this.studentsList = studentsList;
	}
	
	public Quiz() {
		
	}

	public Quiz(String quizName, long instructorId, List<Question> questionList, List<Student> studentsList) {
		super();
		this.quizName = quizName;
		this.instructorId = instructorId;
		this.questionList = questionList;
		this.studentsList = studentsList;
	}


	@Override
	public String toString() {
		return "Quiz [quizId=" + quizId + ", quizName=" + quizName + ", instructorId=" + instructorId + ", questionList="
				+ questionList + ", studentsList=" + studentsList + "]";
	}
	
	

}
