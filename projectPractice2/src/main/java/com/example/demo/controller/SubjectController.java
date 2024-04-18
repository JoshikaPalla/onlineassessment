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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Repository.SubjectRepository;
import com.example.demo.model.Instructor;
import com.example.demo.model.Question;
import com.example.demo.model.Subject;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
import com.example.demo.service.SubjectService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class SubjectController {
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuestionService questionService;
	
	
	private static final Logger logger = LogManager.getLogger(SubjectController.class);
	
	
	
//	@GetMapping("/subjectHomePage")
//	public String viewSubjectHomePage(HttpSession session, Model model){
//		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
//			List<Subject> subjectList = subjectService.findAllSubjects();
//			model.addAttribute("subjectList", subjectList);
//			return "subjecthomePage";
//		}else {
//			String error = "Invalid EmailId or Password";
//			model.addAttribute("error",error);
//			return "loginPage";
//		}
//	}
	
	@GetMapping("/addSubject")
	public String addSubject(Model model, HttpSession session) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((Long) session.getAttribute("userId"));
			Subject subject = new Subject();
			session.setAttribute("subjectId", subject.getSubjectId());
			model.addAttribute("subject", subject);
			model.addAttribute("instructor",instructor);
			System.out.println("addsubject");
			return "addSubject";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
							
	}
	@PostMapping("/addSubjectToList")
	public String addSubjectToList(@ModelAttribute("subject") Subject subject,Model model,HttpSession session) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			subject.setInstructorId(instructor.getInstructorId());
			System.out.println(instructor.getInstructorId());
			subjectService.addSubject(subject);
			logger.info("subject " + subject.getSubjectName() + " is created");
			session.getAttribute("subjectId");
			System.out.println(session.getAttribute("subjectId"));
			model.addAttribute("subject", subject);
			model.addAttribute("instructor",instructor);
			return "redirect:/subjectHomePage";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/updateSubject/{subjectId}")
	public String viewUpdateSubject(HttpSession session, Model model,@PathVariable (value= "subjectId") long subjectId){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Subject subject = subjectService.getBySubjectId(subjectId);
			model.addAttribute("subject", subject);
			return "updateSubject";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	@PostMapping("/updateSubjectToList")
	public String updateSubject(@ModelAttribute Subject subject, Model model, HttpSession session) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			subjectService.updateSubject(subject);
			logger.info("subject " + subject.getSubjectName() + " is updated");
			return "redirect:/subjectHomePage";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
							
	}
	
	
	@GetMapping("/deleteSubject/{subjectId}")
	public String deleteSubject(Model model, HttpSession session,@PathVariable (value= "subjectId") long subjectId ) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			subjectService.deleteSubject(subjectId);
			logger.info("subject with " + subjectId + " is deleted");
			return "redirect:/subjectHomePage";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "redirect:/login";
		}
							
	}
	
	@GetMapping("/viewQuestionBank/{subjectId}")
	public String viewQuestionBank(HttpSession session, Model model,@PathVariable (value= "subjectId") long subjectId ){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			model.addAttribute("subject",subjectService.getBySubjectId(subjectId));
			List<Question> questionList = questionService.findAllQuestions(subjectId);
			if(questionList.isEmpty()) {
				logger.info("No question is added to subject with subjectId " + subjectId);
			}
			model.addAttribute("questionList", questionList);
			session.setAttribute("subjectId",subjectId);
			System.out.println(subjectId);
			return "questionBank";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/viewQuestionBank")
	public String viewQuestionBankAfterAction(HttpSession session, Model model,@ModelAttribute("subject") Subject subject,@ModelAttribute("instructor") Instructor instructor){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			long subjectId = (long) session.getAttribute("subjectId");
			List<Question> questionList = questionService.findAllQuestions(subjectId);
			model.addAttribute("questionList", questionList);
			model.addAttribute("instructor", instructor);
			model.addAttribute("subject", subject);
			return "questionBank";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	
}
