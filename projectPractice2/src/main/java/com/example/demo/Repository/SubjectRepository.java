package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Question;
import com.example.demo.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long>{
	
}

