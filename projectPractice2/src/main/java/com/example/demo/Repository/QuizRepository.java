package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long>{
	List<Quiz> getByInstructorId(long instructorId); 
	
	
	
}
