package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Question;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
	List<Question> findBySubjectId(long subjectId);

	void deleteAllBySubjectId(long subjectId);
}
