package com.lgy.smile.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {	
	
	@GetMapping("/list")
	public String mainList() {
		return "feed/list";
	}	
	
	@GetMapping("/write")
	public String mainWrite() {
		return "feed/write";
	}
	
}
