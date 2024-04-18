package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Quiz;
import com.example.demo.model.Student;
import com.example.demo.model.Submission;
import com.example.demo.service.QuizService;
import com.example.demo.service.SubmissionService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private SubmissionService submissionService;
	
	private static final Logger logger = LogManager.getLogger(StudentController.class);
	
	@GetMapping("/studentHomePage")
	public String viewStudentPage(HttpSession session,Model model){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))){
			return "studentHomePage";
		}else {
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/studentQuizList")
	public String viewStudentQuizList(HttpSession session, Model model) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))){
			Student student = userService.getByStudentId((Long) session.getAttribute("userId"));
			List<Quiz> quizList = student.getQuizes();
			if(quizList.isEmpty()) {
				logger.info("No quiz is assigned to student " + student.getStudentName());
			}
			List<Quiz> quizListNew = new ArrayList<Quiz>();
			for(Quiz quiz: quizList) {
				Submission sub = submissionService.findByStudentIdQuizId((long) session.getAttribute("userId"), quiz.getQuizId());
				if(sub == null) {
					quizListNew.add(quiz);
				}
			}
			model.addAttribute("quizList", quizListNew);
			return "studentQuizList";
		}
		return "redirect:/login";
	}

}
