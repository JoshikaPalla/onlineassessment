package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Instructor {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long instructorId;
	
	@Column
	private String instructorName;
	
	@Column
	private String instructorEmailId;
	
	@Column
	private String instructorPassword;

	public long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(long instructorId) {
		this.instructorId = instructorId;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public String getInstructorEmailId() {
		return instructorEmailId;
	}

	public void setInstructorEmailId(String instructorEmailId) {
		this.instructorEmailId = instructorEmailId;
	}

	public String getInstructorPassword() {
		return instructorPassword;
	}

	public void setInstructorPassword(String instructorPassword) {
		this.instructorPassword = instructorPassword;
	}
	
	public Instructor() {
		super();
	}

	public Instructor(String instructorName, String instructorEmailId, String instructorPassword) {
		super();
		this.instructorName = instructorName;
		this.instructorEmailId = instructorEmailId;
		this.instructorPassword = instructorPassword;
	}

	@Override
	public String toString() {
		return "Instructor [instructorId=" + instructorId + ", instructorName=" + instructorName
				+ ", instructorEmailId=" + instructorEmailId + ", instructorPassword=" + instructorPassword + "]";
	}
	
	
}
