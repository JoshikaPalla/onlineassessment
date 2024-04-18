package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Long> {

	Instructor getByInstructorEmailId(String instructorEmailId);
	
}

