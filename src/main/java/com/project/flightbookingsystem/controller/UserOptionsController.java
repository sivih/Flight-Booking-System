package com.project.flightbookingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserOptionsController {

	    @GetMapping("/useroptions")
	    public String showUserOptions(HttpSession session, Model model) {
	        boolean isLoggedIn = session.getAttribute("loggedInUser") != null;
	        model.addAttribute("isLoggedIn", isLoggedIn);
	        return "useroptions";
	    }

	    @GetMapping("/register")
	    public String showRegisterPage() {
	        return "register"; 
	    }

	    @GetMapping("/login")
	    public String showLoginPage() {
	        return "login";  
	    }
	}