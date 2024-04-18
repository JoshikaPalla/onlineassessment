package com.example.demo.controller;

import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserService;




@Controller
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LogManager.getLogger(RegistrationController.class);

	
	@GetMapping("/register")
	public String viewRegistrationPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		System.out.println("hi");
		return "registrationPage";
	}
	
	@PostMapping("/saveUser")
	public String save(@ModelAttribute("user") User user,Model model) {
		if(userService.getByUserEmailId(user.getUserEmailId()) != null) {
			String error = "This EmailId already in exist login to continue";
			model.addAttribute("error",error);
			System.out.println("email" + user);
			return "registrationPage";
		}else {
			userService.saveUser(user);
			logger.info(user.getUserName() + " is registered");
			return "loginPage";
		}
	}
	
	
	
	
}
