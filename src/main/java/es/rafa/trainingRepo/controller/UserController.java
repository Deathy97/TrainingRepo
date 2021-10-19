package es.rafa.trainingRepo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.rafa.trainingRepo.userService.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/getAllUsers1")
	public String getAllUsers(Model model) {
		model.addAttribute("userList", userService.getAllUsers());

		return "UserList";

	}
	
	@GetMapping("/getAllUsers")
	public String getAllUsers( @RequestParam(defaultValue = "0") Integer pageNo, Model model) {
		model.addAttribute("userList", userService.getPaginationUsers(pageNo));
		model.addAttribute("numPages", userService.getNumberOfPages());

		return "UserList";

	}

}
