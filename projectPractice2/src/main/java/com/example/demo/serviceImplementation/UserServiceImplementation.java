package com.example.demo.serviceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.InstructorRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.model.Instructor;
import com.example.demo.model.Student;
import com.example.demo.model.User;
import com.example.demo.service.UserService;



@Service
public class UserServiceImplementation implements UserService {
	
	@Autowired
	private InstructorRepository instructorRepository;
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public void saveUser(User user) {
		if(user.getUserRole().equalsIgnoreCase("instructor")) {
			Instructor instructor = new Instructor();
			instructor.setInstructorName(user.getUserName());
			instructor.setInstructorEmailId(user.getUserEmailId());
			instructor.setInstructorPassword(user.getUserPassword());
			instructorRepository.save(instructor);
		}else {
			Student student = new Student();
			student.setStudentName(user.getUserName());
			student.setStudentEmailId(user.getUserEmailId());
			student.setStudentPassword(user.getUserPassword());
			studentRepository.save(student);
		}
			
	}

	@Override
	public User getByUserEmailId(String userEmailId) {
		User user = new User();
		Instructor instructor = instructorRepository.getByInstructorEmailId(userEmailId);
		Student student = studentRepository.getByStudentEmailId(userEmailId);
		if(instructor != null) {
			user.setUserId(instructor.getInstructorId());
			user.setUserName(instructor.getInstructorName());
			user.setUserEmailId(instructor.getInstructorEmailId());
			user.setUserPassword(instructor.getInstructorPassword());
			user.setUserRole("instructor");
			return user;
		}else if(student != null){
			user.setUserId(student.getStudentId());
			user.setUserName(student.getStudentName());
			user.setUserEmailId(student.getStudentEmailId());
			user.setUserPassword(student.getStudentPassword());
			user.setUserRole("student");
			return user;
		}else {
			return null;
		}
		
	}

	@Override
	public Student getByStudentId(Long userId) {
		Student student = studentRepository.getById(userId);
		return student;
	}
	
	@Override
	public Instructor getByInstructorId(Long userId) {
		Instructor instructor = instructorRepository.getById(userId);
		return instructor;
	}
}
