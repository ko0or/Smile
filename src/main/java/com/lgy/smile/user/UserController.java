package com.lgy.smile.user;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	@GetMapping("/info")
	public String userInfo(HttpSession session) {
		if (session.getAttribute("userInfo") == null ) {
			return "redirect:login";
		}
		return "user/info";
	}
	
	@GetMapping("/login")
	public String userLogin() {
		return "user/login";
	}
	
	@GetMapping("/createAccount")
	public String userCreateAccount() {
		return "user/createAccount";
	}
	
	
	
	
}
