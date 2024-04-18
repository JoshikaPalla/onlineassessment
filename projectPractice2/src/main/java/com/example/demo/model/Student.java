package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Student {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long studentId;
	
	@Column
	private String studentName;
	
	@Column
	private String studentEmailId;
	
	@Column
	private String studentPassword;
	
	@ManyToMany(mappedBy = "studentsList", cascade = { CascadeType.ALL })
    private List<Quiz> quizes;

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentEmailId() {
		return studentEmailId;
	}

	public void setStudentEmailId(String studentEmailId) {
		this.studentEmailId = studentEmailId;
	}

	public String getStudentPassword() {
		return studentPassword;
	}

	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}
	
	public List<Quiz> getQuizes() {
		return quizes;
	}

	public void setQuizes(List<Quiz> quizes) {
		this.quizes = quizes;
	}

	public Student() {
		super();
	}

	public Student(String studentName, String studentEmailId, String studentPassword) {
		super();
		this.studentName = studentName;
		this.studentEmailId = studentEmailId;
		this.studentPassword = studentPassword;
	}

	public Student(String studentName, String studentEmailId, String studentPassword, List<Quiz> quizes) {
		super();
		this.studentName = studentName;
		this.studentEmailId = studentEmailId;
		this.studentPassword = studentPassword;
		this.quizes = quizes;
	}

//	@Override
//	public String toString() {
//		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", studentEmailId=" + studentEmailId
//				+ ", studentPassword=" + studentPassword + ", quizes=" + quizes + "]";
//	}
	
	

	
	
	
	
}
