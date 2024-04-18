package com.example.demo.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Instructor;
import com.example.demo.model.Question;
import com.example.demo.model.Subject;
import com.example.demo.service.QuestionService;
import com.example.demo.service.SubjectService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;
@Controller
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SubjectService subjectService;
	
	private static final Logger logger = LogManager.getLogger(QuestionController.class);
	

	
	
	
	@GetMapping("/addQuestion")
	public String addQuestion(Model model, HttpSession session) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			Subject subject = subjectService.getBySubjectId((long) session.getAttribute("subjectId"));
			Question question = new Question();
			model.addAttribute("subjectId", subject.getSubjectId());
			model.addAttribute("instructorId",instructor.getInstructorId());
			model.addAttribute("question", question);
			logger.info(instructor.getInstructorName() + " is returning to add question page to add a question to " + subject.getSubjectName());
			return "addQuestion";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
							
	}
	@PostMapping("/addQuestionToList")
	public String addQuestionToList(Model model,HttpSession session,@ModelAttribute("question") Question question) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			Subject subject = subjectService.getBySubjectId((long)(session.getAttribute("subjectId")));
			question.setInstructorId(instructor.getInstructorId());
			question.setSubjectId(subject.getSubjectId());
			System.out.println(session.getAttribute("subjectId"));
			questionService.addQuestion(question);
			model.addAttribute("question", question);
			logger.info("A question is added to " + subject.getSubjectName() + " by " + instructor.getInstructorName());
			return "redirect:/viewQuestionBank";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/deleteQuestion/{questionId}")
	public String deleteQuestion(Model model, HttpSession session,@PathVariable (value= "questionId") long questionId) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			System.out.println(questionId);
			questionService.deleteQuestion(questionId);
			logger.info("A question with questionId " + questionId + " is deleted by instructor ");
			return "redirect:/viewQuestionBank";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "redirect:/login";
		}
							
	}
	
	
	
	
	@GetMapping("/updateQuestion/{questionId}")
	public String viewUpdateQuestion(HttpSession session, Model model,@PathVariable (value= "questionId") long questionId){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Question question = questionService.getByQuestionId(questionId);
			System.out.println(questionId);
			model.addAttribute("question", question);
			logger.info("instructor is returning to update question page to update question with questionId "+ questionId);
			return "updateQuestion";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	@PostMapping("/updateQuestionToList")
	public String updateQuestion(Model model, HttpSession session,@ModelAttribute Question question) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			questionService.updateQuestion(question);
			logger.info("instructor updated question with questionId " + question.getQuestionId() );
			return "redirect:/viewQuestionBank";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
							
	}
	
}
