package es.rafa.trainingRepo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model modelo) {
		modelo.addAttribute("saludo", "Alo presidente");
		return "Hola";
	}
}
