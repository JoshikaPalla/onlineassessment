package com.example.demo.service;

import com.example.demo.model.Instructor;
import com.example.demo.model.Student;
import com.example.demo.model.User;

public interface UserService {
	void saveUser(User user);
	User getByUserEmailId(String userEmailId);
	Student getByStudentId(Long userId);
	Instructor getByInstructorId(Long userId);
}
