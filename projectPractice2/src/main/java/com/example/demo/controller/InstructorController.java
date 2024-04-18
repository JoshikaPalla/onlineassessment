package com.example.demo.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;



import com.example.demo.model.Instructor;
import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.model.Submission;
import com.example.demo.service.QuizService;
import com.example.demo.service.StudentService;
import com.example.demo.service.SubjectService;
import com.example.demo.service.SubmissionService;
import com.example.demo.service.UserService;
import com.example.demo.serviceImplementation.QuizServiceImplementation;
import com.example.demo.serviceImplementation.StudentServiceImplementation;
import com.example.demo.serviceImplementation.SubjectServiceImplementation;
import com.example.demo.serviceImplementation.SubmissionServiceImplementation;
import com.example.demo.serviceImplementation.UserServiceImplementation;

import jakarta.servlet.http.HttpSession;

@Controller
public class InstructorController {
	 
	@Autowired
	 private UserService userService;
	
	@Autowired
	 private StudentService studentService;
	
	@Autowired
	 private SubmissionService submissionService;
	
	@Autowired
	private SubjectService subjectService ;
	
	@Autowired
	private QuizService quizService ;
	
	

	
	private static final Logger logger = LogManager.getLogger(InstructorController.class);
	
	@GetMapping("/instructorHomePage")
	public String viewInstructorHomePage(HttpSession session, Model model){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			model.addAttribute("instructor",instructor);
			logger.info("Returning a home page of instructor "+instructor.getInstructorName());
			return "instructorHomePage";
		}else {
			logger.error("Invalid Email Id or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/subjectHomePage")
	public String viewSubjectHomePage(HttpSession session, Model model){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			List<Subject> subjectList = subjectService.findAllSubjects();
			if(subjectList.isEmpty()) {
				logger.info("subjectList is empty");
			}
			logger.info("subjectList contains " + subjectList);
			model.addAttribute("subjectList", subjectList);
			model.addAttribute("instructor",instructor);
			logger.info(instructor.getInstructorName() + " returning to a subjects page");
			return "subjectHomePage";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/studentPerformace")
	public String viewStudentPerformance(HttpSession session, Model model){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((Long) session.getAttribute("userId"));
			logger.info(instructor.getInstructorName() + " returning to a students performance page");
			return "studentPerformancePage";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/instructorQuizList")
	public String viewInstructorQuizList(HttpSession session, Model model) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor= userService.getByInstructorId((Long) session.getAttribute("userId"));
			List<Quiz> quizList = quizService.findAllQuizByInstructorId(instructor.getInstructorId());
			if(quizList.isEmpty()) {
				logger.info(instructor.getInstructorName() + " returning to a quizList is empty");
			}
			logger.info(instructor.getInstructorName() + " returning to quizList page containing quizes " );
			model.addAttribute("quizList", quizList);
			return "instructorQuizList";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/viewStudentsPerformance")
	public String viewStudentsPerformance(HttpSession session, Model model) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor= userService.getByInstructorId((Long) session.getAttribute("userId"));
			List<Student> studentList = studentService.findAllStudents();
			if(studentList.isEmpty()) {
				logger.info(instructor.getInstructorName() + " returning to a studentList is empty");
			}
			logger.info(instructor.getInstructorName() + " returning to students performance page  " );
			model.addAttribute("studentList", studentList);
			return "viewStudentsPerformance";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/performance/{studentId}")
	public String viewPerformance(HttpSession session,@PathVariable("studentId") long studentId,Model model) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Student student = userService.getByStudentId(studentId);
			session.setAttribute("studentId", studentId);
			System.out.println(studentId);
			Submission submission = submissionService.findByStudentId(studentId);
			if(submission == null) {
				List<Quiz> quizList = student.getQuizes();
				if(quizList.isEmpty()){
					logger.info(student.getStudentName() +" is not assigned to any quiz");
				}
				HashMap<Quiz,String> performanceMap = new HashMap<Quiz,String>();
				for(Quiz quiz:quizList) {
					
					performanceMap.put(quiz, "Not Attempted");
				}
				model.addAttribute("performanceMap",performanceMap);
				logger.info("instructor is returning to view performance of " + student.getStudentName());
				return "instructorStudentPerformance";
			}
			
			List<Quiz> quizList = student.getQuizes();
			
			HashMap<Quiz,String> performanceMap = new HashMap<Quiz,String>();
			for(Quiz quiz:quizList) {
				String performance = submissionService.findByStudentIdAndQuizId(student.getStudentId(), quiz.getQuizId());
				performanceMap.put(quiz, performance);
			}
			model.addAttribute("performanceMap",performanceMap);
			logger.info("instructor is returning to view performance of " + student.getStudentName());
			return "instructorStudentPerformance";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
				
	}
	
	@GetMapping("/viewPaper/{quizId}")
	public String displayPaper(HttpSession session,@PathVariable(value= "quizId") long quizId,Model model) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)session.getAttribute("userId"));
			Quiz quiz = quizService.getByQuizId(quizId);
			Student student = userService.getByStudentId((long)session.getAttribute("studentId"));
			List<Submission> submissionList = submissionService.findAllByStudentIdQuizId(student.getStudentId(), quizId);
			if(submissionList.isEmpty()) {
				Map<Question, String> studentPerformanceMap = new HashMap<>();
				List<Question> questionList = quiz.getQuestionList();
				for(Question question : questionList) {
					
					studentPerformanceMap.put(question,submissionService.getByQuestionIdAndStudentId(question.getQuestionId(), student.getStudentId(), quizId) );
					
				}
				model.addAttribute("studentPerformanceMap",studentPerformanceMap);
				return "studentViewPaper";
			}else {
			int iterator = 0;
			Map<Question, String> studentPerformanceMap = new HashMap<>();
			List<Question> questionList = quiz.getQuestionList();
			for(Question question : questionList) {
				studentPerformanceMap.put(question, submissionList.get(iterator).getSelectedOption());
				iterator++;
			}
			model.addAttribute("studentPerformanceMap",studentPerformanceMap);
			return "studentViewPaper";
		}
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	
	
	

	
}