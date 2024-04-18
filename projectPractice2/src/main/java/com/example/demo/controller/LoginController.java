package com.example.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Student;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Instructor;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@GetMapping("/login")
	public String viewLoginPage(Model model) {
		logger.info("returning to a login page");
		return "loginPage";
	}
	
	@PostMapping("/loginUser")
	public String save(@RequestParam String EmailID,@RequestParam String Password,Model model, HttpServletRequest request)  {
		User user = userService.getByUserEmailId(EmailID);
		if((user != null) && (user.getUserPassword().equals(Password))) {
			HttpSession session = request.getSession();
			session.setAttribute("userId",user.getUserId());
			session.setAttribute("userName", user.getUserName());
			session.setAttribute("userEmailId", user.getUserEmailId());
			session.setAttribute("userRole", user.getUserRole());
			if(user.getUserRole().equalsIgnoreCase("instructor")) {
				logger.info("instructor logged in :" + user.getUserName());
				return "instructorHomePage";
			}else {
				logger.info("student logged in :" + user.getUserName());
				return "studentHomePage";
			}
		}else {
			
			if(user==null) {
				logger.error("Invalid login attempt" );
				throw new UserNotFoundException("Invalid Email Id");
				
			}
			
			String error = "Invalid EmailId or Password";
			logger.error("Invalid login attempt" );
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/profile")
	public String profile(Model model, HttpServletRequest request,HttpSession session) {
		if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("student"))){
			Student student = userService.getByStudentId((Long) session.getAttribute("userId"));
			logger.info("returning to student profile of " + student.getStudentName());
			return "profile";
		}else if((session.getAttribute("userRole") != null) && (session.getAttribute("userRole").equals("instructor"))){
			Instructor instructor = userService.getByInstructorId((Long) session.getAttribute("userId"));
			logger.info("returning to instructor profile of " + instructor.getInstructorName());
			return "instructorProfile";
		} else {
			logger.error("Invalid attempt to login  " );
			String error = "Invalid EmailId or Password";
			model.addAttribute("error",error);
			return "loginPage";
		}
	}
	
	@GetMapping("/loginOut")
	public String logout(HttpSession session) {
		session.invalidate();
		logger.info("user logged out");
		return "redirect:/login";
	}
}
