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
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.model.Instructor;
import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.service.QuestionService;
import com.example.demo.service.QuizService;
import com.example.demo.service.StudentService;
import com.example.demo.service.SubjectService;
import com.example.demo.service.SubmissionService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class QuizController {
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private SubmissionService submissionService;
	
	private static final Logger logger = LogManager.getLogger(QuizController.class);
	
	@GetMapping("/addQuiz")
	public String addQuiz(Model model, HttpSession session) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((Long) session.getAttribute("userId"));
			Quiz quiz = new Quiz();
			model.addAttribute("quiz", quiz);
			model.addAttribute("instructor",instructor);
			logger.info("instructor " + instructor.getInstructorName() + " is returning to add quiz page to create a quiz");
			return "addQuiz";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
							
	}
	@PostMapping("/addQuizToList")
	public String addQuiztToList(@ModelAttribute("quiz") Quiz quiz,Model model,HttpSession session) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			quiz.setInstructorId(instructor.getInstructorId());
			quizService.addQuiz(quiz);
			logger.info("instructor " + instructor.getInstructorName() + " created a quiz with name " + quiz.getQuizName());
			List<Question> questionList = quiz.getQuestionList();
			if(questionList.isEmpty()) {
				logger.info("No questions are added to this quiz");
			}
			List<Student> studentsList = quiz.getStudentsList();
			if(studentsList.isEmpty()) {
				logger.info("No students are added to this quiz");
			}
			session.setAttribute("quizId",quiz.getQuizId());
			session.setAttribute("quizName", quiz.getQuizName());
			session.setAttribute("questionList",questionList);
			session.setAttribute("studentsList",studentsList);
			model.addAttribute("quiz", quiz);
			model.addAttribute("quizName", quiz.getQuizName());
			model.addAttribute("instructor",instructor);
			logger.info("redirectiong to a instructor quiz List page");
			return "redirect:/instructorQuizList";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/viewQuiz/{quizId}")
	public String viewQuiz(HttpSession session, Model model,@PathVariable (value= "quizId") long quizId ){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			model.addAttribute("instructor",instructor);
			Quiz quiz = quizService.getByQuizId(quizId);
			model.addAttribute("quiz",quiz);
			List<Question> questionList = quiz.getQuestionList();
			session.setAttribute("quiz", quiz);
			session.setAttribute("quizId", quizId);
			session.setAttribute("quizName", quiz.getQuizName());
			session.setAttribute("questionList", questionList);
			model.addAttribute("questionList", questionList);
			List<Student> studentsList = quiz.getStudentsList();
			session.setAttribute("studentsList", studentsList);
			model.addAttribute("studentsList", studentsList);
			logger.info("instructor is returning to view quiz page");
			return "viewQuiz";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/addQuestionToQuiz/{quizId}")
	public String AddQuestionToList(HttpSession session, Model model,@PathVariable (value= "quizId") long quizId ){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			model.addAttribute("instructor",instructor);
			Quiz quiz = quizService.getByQuizId(quizId);
			session.setAttribute("quiz", quiz);
			model.addAttribute("quiz",quiz);
			List<Subject> subjectList = subjectService.findAllSubjects();
			session.setAttribute("subjectList", subjectList);
			model.addAttribute("subjectList", subjectList);
			List<Question> questionList = quiz.getQuestionList();
			session.setAttribute("questionList", questionList);
			model.addAttribute("questionList", questionList);
			List<Student> studentsList = quiz.getStudentsList();
			session.setAttribute("studentsList", studentsList);
			model.addAttribute("studentsList", studentsList);
			return "addQuestionToQuiz";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/viewQuestionsFromSubject/{subjectId}")
	public String viewQuestionsFromSubject(HttpSession session, Model model,@PathVariable (value= "subjectId") long subjectId ){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			Subject subject = subjectService.getBySubjectId(subjectId);
			List<Question> questionList = questionService.findAllQuestions(subjectId);
			model.addAttribute("questionList", questionList);
			long quizId = (long) session.getAttribute("quizId");
			model.addAttribute("quizId", quizId);
			System.out.println(quizId);
			session.setAttribute("subjectId", subjectId);
			return "viewQuestionsFromSubject";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/viewQuestionsFromSubject")
	public String viewQuestionsFromSubjectAfterAdd(HttpSession session, Model model ){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			Subject subject = subjectService.getBySubjectId((long)(session.getAttribute("subjectId")));
			System.out.println(subject);
			List<Question> questionList = questionService.findAllQuestions(subject.getSubjectId());
			model.addAttribute("questionList", questionList);
			long quizId = (long) session.getAttribute("quizId");
			model.addAttribute("quizId", quizId);
			System.out.println(quizId);
			
			return "viewQuestionsFromSubject";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/addQuestionFromSubjectToQuiz/{questionId}")
	public String addQuestionFromSubjectToQuiz(HttpSession session, Model model,@PathVariable (value= "questionId") long questionId) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			Quiz quiz = quizService.getByQuizId((long) session.getAttribute("quizId"));
			System.out.println(questionId);
			Question question = questionService.getByQuestionId(questionId);
			quiz.getQuestionList().add(question);
			
			quizService.addQuiz(quiz);
			logger.info(" A question with questionId" + question.getQuestionId() + " is added to quiz " + quiz.getQuizName() +" by " +instructor.getInstructorName());

			return "redirect:/viewQuestionsFromSubject";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/addStudent/{quizId}")
	public String AddStudentToList(HttpSession session, Model model,@PathVariable (value= "quizId") long quizId ){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			model.addAttribute("instructor",instructor);
			Quiz quiz = quizService.getByQuizId(quizId);
			session.setAttribute("quizId", quizId);
			model.addAttribute("quiz",quiz);
			List<Student> studentsList = studentService.findAllStudents();
			session.setAttribute("studentsList", studentsList);
			model.addAttribute("studentsList", studentsList);
			return "addStudentToQuiz";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/addStudent")
	public String AddStudentToListAfterAdd(HttpSession session, Model model){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			model.addAttribute("instructor",instructor);
			Quiz quiz = quizService.getByQuizId((long) session.getAttribute("quizId"));
			session.setAttribute("quiz", quiz);
			model.addAttribute("quiz",quiz);
			List<Student> studentsList = studentService.findAllStudents();
			session.setAttribute("studentsList", studentsList);
			model.addAttribute("studentsList", studentsList);
			
			return "addStudentToQuiz";
		}else {
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	
	
	
	
	@GetMapping("/addStudentFromStudentListToQuiz/{studentId}")
	public String addStudentToQuiz(HttpSession session, Model model,@PathVariable (value= "studentId") long studentId) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			Quiz quiz = quizService.getByQuizId((long) session.getAttribute("quizId"));
			if(quiz.getQuestionList().isEmpty()) {
				throw new QuestionNotFoundException("Add Questions to quiz");
			}

			Student student = userService.getByStudentId(studentId);
			quiz.getStudentsList().add(student);
			quizService.addQuiz(quiz);
			logger.info("student " + student.getStudentName() + " is assigned to quiz " + quiz.getQuizName() + " by " + instructor.getInstructorName());
			return "redirect:/addStudent";
		}else {
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	
	@GetMapping("/questionList/{quizId}")
	public String viewQuestionList(HttpSession session, Model model,@PathVariable (value= "quizId") long quizId) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			Quiz quiz = quizService.getByQuizId((long) session.getAttribute("quizId"));
			List<Question> questionList = quiz.getQuestionList();
			model.addAttribute("questionList", questionList);
			return "viewQuestionList";
		}else {
			logger.error("Invalid EmailId or Password");
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/studentList/{quizId}")
	public String viewStudentsList(HttpSession session, Model model,@PathVariable (value= "quizId") long quizId) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((long)(session.getAttribute("userId")));
			Quiz quiz = quizService.getByQuizId((long) session.getAttribute("quizId"));
			List<Student> studentsList = quiz.getStudentsList();
			Map<Student,String> performanceMap = new HashMap<>();
			for(Student student:studentsList) {
				String performance = submissionService.findByStudentIdAndQuizId(student.getStudentId(), quiz.getQuizId());
				performanceMap.put(student, performance);
			}
			model.addAttribute("performanceMap", performanceMap);
			return "viewStudentsList";
		}else {
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/deleteQuiz/{quizId}")
	public String deleteQuiz(Model model, HttpSession session,@PathVariable (value= "quizId") long quizId) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			System.out.println(quizId);
			quizService.deleteQuiz(quizId);
			logger.info("quiz with quizId "  + quizId + "is deleted" );
			return "redirect:/instructorQuizList";
		}else {
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "redirect:/login";
		}
							
	}
	
	@GetMapping("/updateQuiz/{quizId}")
	public String viewUpdateQuiz(HttpSession session, Model model,@PathVariable (value= "quizId") long quizId){
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Quiz quiz = quizService.getByQuizId(quizId);
			System.out.println(quizId);
			model.addAttribute("quiz", quiz);
			return "updateQuiz";
		}else {
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@PostMapping("/updateQuizToList")
	public String updateQuiz(Model model, HttpSession session,@ModelAttribute Quiz quiz) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			quizService.updateQuiz(quiz);
			logger.info("quiz "+ quiz.getQuizName() + " is updated" );
			return "redirect:/instructorQuizList";
		}else {
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
							
	}
	

	
}
