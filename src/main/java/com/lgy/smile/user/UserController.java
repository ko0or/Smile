package com.lgy.smile.user;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	
	// ★ user(유저) 정보 조회화면
	@GetMapping("/info")
	public String userInfo(HttpSession session) {
		if (session.getAttribute("userInfo") == null ) {
			return "redirect:login";
		}
		return "user/info";
	}

	
	// ★ user(유저) 로그인 화면
	@GetMapping("/login")
	public String userLogin() {
		return "user/login";
	}

		
	
	// ★ user(유저) 회원가입 화면
	@GetMapping("/createAccount")
	public String userCreateAccount() {
		return "user/createAccount";
	}
	
	

	
}
