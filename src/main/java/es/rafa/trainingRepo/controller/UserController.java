package es.rafa.trainingRepo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ès.rafa.trainingRepo.userService.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;

	
	@GetMapping("/getAllUsers")
	public String getAllUsers(Model model) {
		model.addAttribute("userList",userService.getAllUsers());
		return "UserList";
		
	}
}
