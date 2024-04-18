package com.example.demo.controller;

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

import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.SubmissionRepository;
import com.example.demo.exception.AnswerNotFoundException;
import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.model.Student;
import com.example.demo.model.Submission;
import com.example.demo.service.QuestionService;
import com.example.demo.service.QuizService;
import com.example.demo.service.SubmissionService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentQuizListController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private SubmissionService submissionService;
	
	@Autowired
	private SubmissionRepository submissionRepository ;
	
	
	
	private static final Logger logger = LogManager.getLogger(StudentQuizListController.class);
	
	@GetMapping("/startQuiz/{quizId}")
	public String displayQuestions(HttpSession session,@PathVariable(value= "quizId") long quizId,Model model) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))){
			Student student = userService.getByStudentId((Long) session.getAttribute("userId"));
			Quiz quiz = quizService.getByQuizId(quizId);
			List<Question> questionList = quiz.getQuestionList();
			session.setAttribute("quizId", quizId);
			session.setAttribute("questionList", questionList);
			int iterator = 0;
			session.setAttribute("iterator",iterator);
			model.addAttribute("question",questionList.get(iterator));
			session.setAttribute("questionId", questionList.get(iterator).getQuestionId());
			session.setAttribute("totalMarks", questionList.get(iterator).getMarks());
			session.setAttribute("securedMarks", 0);
			Submission submission = new Submission();
			return "startQuiz";
		}else {
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	
	@GetMapping("/submitQuiz")
	public String nextAndSubmitQuiz(HttpSession session,@ModelAttribute("submission") Submission submission ,Model model) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))){
			if(submission.getSelectedOption()==null) {
				logger.error("option is not selected");
				throw new AnswerNotFoundException("select your option");
			}
			Student student = userService.getByStudentId((Long) session.getAttribute("userId"));
			long questionId = (long) session.getAttribute("questionId");
			submission.setQuestionId(questionId);
			Question question = questionService.getByQuestionId(questionId);
			submission.setQuizId((long) session.getAttribute("quizId"));
			submission.setStudentId((long) session.getAttribute("userId"));
			System.out.println(question.getCorrectAnswer());
			System.out.println(submission.getSelectedOption());
			if(submission.getSelectedOption().equals((question.getCorrectAnswer()))) {
				int securedMarks = (int) session.getAttribute("securedMarks");
				securedMarks += question.getMarks();
				System.out.println(securedMarks);
				session.setAttribute("securedMarks", securedMarks);
			}
			
			submissionService.addSubmission(submission);
			Quiz quiz = quizService.getByQuizId((long) session.getAttribute("quizId"));
			List<Question> questionList = quiz.getQuestionList();
			System.out.println(questionList);
			
			int iterator = (int) session.getAttribute("iterator");
			if(questionList.size() > iterator+1) {
				session.setAttribute("iterator",iterator+1);
				model.addAttribute("question",questionList.get(iterator+1));
				session.setAttribute("questionId", questionList.get(iterator+1).getQuestionId());
				int totalMarks = (int) session.getAttribute("totalMarks");
				session.setAttribute("totalMarks", totalMarks + questionList.get(iterator+1).getMarks());
				session.setAttribute("securedMarks",session.getAttribute("securedMarks") );
				return "startQuiz";
			}else {
				model.addAttribute("totalMarks" ,session.getAttribute("totalMarks"));
				model.addAttribute("securedMarks",session.getAttribute("securedMarks") );
				return "scoreCard";
			}
			
			
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	
	@GetMapping("/performance")
	public String viewPerformance(HttpSession session,@ModelAttribute("submission") Submission submission ,Model model) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))){
			Student student = userService.getByStudentId((Long) session.getAttribute("userId"));
			List<Quiz> quizList = student.getQuizes();
			HashMap<Quiz,String> performanceMap = new HashMap<Quiz,String>();
			for(Quiz quiz:quizList) {
				String performance = submissionService.findByStudentIdAndQuizId(student.getStudentId(), quiz.getQuizId());
				if(performance.equalsIgnoreCase("Not Attempted")) {
					
				}else {
					performanceMap.put(quiz, performance);
					logger.info("performance of a student " + student.getStudentName() + " is calculated for quiz " + quiz.getQuizName());
				}
			}
			model.addAttribute("performanceMap",performanceMap);
			model.addAttribute("quizList",quizList);
			return "performance";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
				
	}
	
//	@GetMapping("/submission/{quizId}")
//	public String displaySubmission(HttpSession session,@PathVariable(value= "quizId") long quizId,Model model) {
//		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))){
//			Student student = userService.getByStudentId((long)session.getAttribute("userId"));
//			session.setAttribute("quizId", quizId);
//			List<Submission> submissionList = submissionRepository.findByStudentIdAndQuizId(student.getStudentId(), quizId);
//			int iterator = 0;
//			session.setAttribute("iterator",iterator);
//			Question question = questionRepository.getById(submissionList.get(iterator).getQuestionId());
//			model.addAttribute("question",question);
//			model.addAttribute("questionId",question.getQuestionId());
//			model.addAttribute("questionTitle",question.getQuestionTitle());
//			model.addAttribute("option1",question.getOption1());
//			model.addAttribute("option2",question.getOption2());
//			model.addAttribute("option3",question.getOption3());
//			model.addAttribute("option4",question.getOption4());
//		
//			model.addAttribute("correctAnswer",question.getCorrectAnswer());
//			System.out.println(question.getCorrectAnswer());
//			
//			model.addAttribute("selectedOption",submissionList.get(iterator).getSelectedOption());
//				
//			
//			return "performancePage";
//		}else {
//			logger.error("Invalid EmailId or Password");
//			String error = "Invalid EmailId or Password";
//			model.addAttribute("error",error);
//			return "loginPage";
//		}
//	}
	
//	@GetMapping("/next")
//	public String displaynextSubmission(HttpSession session,Model model) {
//		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))){
//			Student student = userService.getByStudentId((Long) session.getAttribute("userId"));
//			long quizId = (long) session.getAttribute("quizId");
//			List<Submission> submissionList = submissionRepository.findByStudentIdAndQuizId(student.getStudentId(), quizId);
//			int iterator = (int) session.getAttribute("iterator");
//			if(submissionList.size() > iterator+1) {
//				session.setAttribute("iterator",iterator+1);
//				Question question = questionRepository.getById(submissionList.get(iterator+1).getQuestionId());
//				model.addAttribute("question",question);
//				model.addAttribute("questionId",question.getQuestionId());
//				model.addAttribute("questionTitle",question.getQuestionTitle());
//				model.addAttribute("option1",question.getOption1());
//				model.addAttribute("option2",question.getOption2());
//				model.addAttribute("option3",question.getOption3());
//				model.addAttribute("option4",question.getOption4());
//				model.addAttribute("correctAnswer",question.getCorrectAnswer());
//				model.addAttribute("selectedOption",submissionList.get(iterator).getSelectedOption());
//				
//				return "performancePage";
//			}else {
//				return "redirect:/performance";
//			}
//		}else {
//			logger.error("Invalid EmailId or Password");
//			String error = "Invalid EmailId or Password";
//			model.addAttribute("error",error);
//			return "loginPage";
//		}
//	}
	
	@GetMapping("/submission/{quizId}")
	public String displaySubmission(HttpSession session,@PathVariable(value= "quizId") long quizId,Model model) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))){
			Student student = userService.getByStudentId((long)session.getAttribute("userId"));
			Quiz quiz = quizService.getByQuizId(quizId);
			session.setAttribute("quizId", quizId);
			List<Submission> submissionList = submissionService.findAllByStudentIdQuizId(student.getStudentId(), quizId);
			int iterator = 0;
			Map<Question, Submission> studentPerformanceMap = new HashMap<>();
			List<Question> questionList = quiz.getQuestionList();
			for(Question question : questionList) {
				studentPerformanceMap.put(question, submissionList.get(iterator));
				iterator++;
			}
			model.addAttribute("studentPerformanceMap",studentPerformanceMap);
			return "performancePage";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
}

	
	
	

